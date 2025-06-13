package org.sci.serviciolibros.repository;

import org.sci.serviciolibros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long>, ILibroRepository {
}
