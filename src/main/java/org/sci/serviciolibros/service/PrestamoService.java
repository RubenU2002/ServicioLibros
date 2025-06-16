package org.sci.serviciolibros.service;

import org.sci.serviciolibros.model.Multa;
import org.sci.serviciolibros.model.Prestamo;
import org.sci.serviciolibros.model.Usuario;
import org.sci.serviciolibros.repository.IPrestamoRepository;
import org.sci.serviciolibros.repository.ILibroRepository;
import org.sci.serviciolibros.repository.IUsuarioRepository;
import org.sci.serviciolibros.repository.IMultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {
    private final IPrestamoRepository prestamoRepository;
    private final ILibroRepository libroRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IMultaRepository multaRepository;
    private final MultaStrategyFactory multaStrategyFactory;

    @Autowired
    public PrestamoService(IPrestamoRepository prestamoRepository,
                          ILibroRepository libroRepository,
                          IUsuarioRepository usuarioRepository,
                          IMultaRepository multaRepository,
                          MultaStrategyFactory multaStrategyFactory) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
        this.multaRepository = multaRepository;
        this.multaStrategyFactory = multaStrategyFactory;
    }

    public Prestamo solicitarPrestamo(Long usuarioId, Long libroId, LocalDate fechaEntrega) {
        var usuarioOpt = usuarioRepository.findById(usuarioId);
        var libroOpt = libroRepository.findById(libroId);
        if (usuarioOpt.isEmpty() || libroOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario o libro no encontrado");
        }
        var libro = libroOpt.get();
        if (!libro.isDisponible()) {
            throw new IllegalStateException("El libro no está disponible");
        }
        libro.setDisponible(false);
        libroRepository.save(libro);
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuarioOpt.get());
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaEntrega(fechaEntrega);
        return prestamoRepository.save(prestamo);
    }

    public Prestamo devolverLibro(Long prestamoId) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(prestamoId);
        if (prestamoOpt.isEmpty()) {
            throw new IllegalArgumentException("Préstamo no encontrado");
        }
        Prestamo prestamo = prestamoOpt.get();
        if (prestamo.getFechaDevolucion() != null) {
            throw new IllegalStateException("El libro ya fue devuelto");
        }
        prestamo.setFechaDevolucion(LocalDate.now());
        // Calcular multa si hay retraso
        long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaEntrega(), prestamo.getFechaDevolucion());
        if (diasRetraso > 0) {
            Multa multa = new Multa();
            multa.setDiasRetraso((int) diasRetraso);
            double monto = multaStrategyFactory.getStrategy(prestamo.getUsuario().getRol()).calcularMulta((int) diasRetraso);
            multa.setMonto(monto);
            multa.setPrestamoId(prestamo.getId());
            multa.setPrestamo(prestamo);
            multaRepository.save(multa);
            prestamo.setMulta(multa);
        }
        // Marcar libro como disponible
        var libro = prestamo.getLibro();
        libro.setDisponible(true);
        libroRepository.save(libro);
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }
}
