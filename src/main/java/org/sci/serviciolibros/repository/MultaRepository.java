package org.sci.serviciolibros.repository;

import org.sci.serviciolibros.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultaRepository extends JpaRepository<Multa, Long>, IMultaRepository {
}
