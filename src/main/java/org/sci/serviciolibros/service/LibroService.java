package org.sci.serviciolibros.service;

import org.sci.serviciolibros.model.Libro;
import org.sci.serviciolibros.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    private final ILibroRepository libroRepository;

    @Autowired
    public LibroService(ILibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Libro crearLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }

    public void actualizarDisponibilidad(Long id, boolean disponible) {
        Optional<Libro> libroOpt = libroRepository.findById(id);
        libroOpt.ifPresent(libro -> {
            libro.setDisponible(disponible);
            libroRepository.save(libro);
        });
    }
}
