package org.sci.serviciolibros.repository;

import org.sci.serviciolibros.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>, IPrestamoRepository {
}
