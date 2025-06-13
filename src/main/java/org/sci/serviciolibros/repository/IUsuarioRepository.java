package org.sci.serviciolibros.repository;

import org.sci.serviciolibros.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    void deleteById(Long id);
}

