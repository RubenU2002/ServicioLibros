package org.sci.serviciolibros.repository;

import org.sci.serviciolibros.model.Libro;
import java.util.List;
import java.util.Optional;

public interface ILibroRepository {
    Libro save(Libro libro);
    List<Libro> findAll();
    Optional<Libro> findById(Long id);
    void deleteById(Long id);
}

