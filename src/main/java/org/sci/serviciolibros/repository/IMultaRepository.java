package org.sci.serviciolibros.repository;

import org.sci.serviciolibros.model.Multa;
import java.util.List;
import java.util.Optional;

public interface IMultaRepository {
    Multa save(Multa multa);
    List<Multa> findAll();
    Optional<Multa> findById(Long id);
    void deleteById(Long id);
}

