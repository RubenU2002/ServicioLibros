package org.sci.serviciolibros.repository;

import org.sci.serviciolibros.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, IUsuarioRepository {
}
