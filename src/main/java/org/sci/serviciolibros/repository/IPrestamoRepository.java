package org.sci.serviciolibros.repository;

import org.sci.serviciolibros.model.Prestamo;
import java.util.List;
import java.util.Optional;

public interface IPrestamoRepository {
    Prestamo save(Prestamo prestamo);
    List<Prestamo> findAll();
    Optional<Prestamo> findById(Long id);
    void deleteById(Long id);
}

