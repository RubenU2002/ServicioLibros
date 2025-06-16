package org.sci.serviciolibros.controller;

import org.sci.serviciolibros.model.Libro;
import org.sci.serviciolibros.model.Multa;
import org.sci.serviciolibros.model.Prestamo;
import org.sci.serviciolibros.model.Usuario;
import org.sci.serviciolibros.repository.ILibroRepository;
import org.sci.serviciolibros.repository.IUsuarioRepository;
import org.sci.serviciolibros.repository.IPrestamoRepository;
import org.sci.serviciolibros.repository.IMultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    @Autowired
    private ILibroRepository libroRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IPrestamoRepository prestamoRepository;
    @Autowired
    private IMultaRepository multaRepository;

    @GetMapping("/libros")
    public List<Libro> libros() {
        return libroRepository.findAll();
    }

    @GetMapping("/libros-prestados")
    public List<Libro> librosPrestados() {
        return libroRepository.findAll().stream()
                .filter(libro -> !libro.isDisponible())
                .collect(Collectors.toList());
    }

    @GetMapping("/usuarios")
    public List<Usuario> usuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/multas")
    public List<Multa> multas() {
        List<Multa> multas = multaRepository.findAll();
        // Almacenar solo el identificador del préstamo hace que la relación se
        // pierda al leer desde archivo. Aquí se reconstruye para enviar al
        // frontend la información completa.
        multas.forEach(m -> {
            if (m.getPrestamo() == null && m.getPrestamoId() != null) {
                prestamoRepository.findById(m.getPrestamoId()).ifPresent(m::setPrestamo);
            }
        });
        return multas;
    }
}
