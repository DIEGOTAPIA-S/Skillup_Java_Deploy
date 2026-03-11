package com.skillup.skillup.repository;

import com.skillup.skillup.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, String> {

 List<Usuario> findByIdRol(Integer idRol);

    Optional<Usuario> findByIdentificacion(String identificacion);

    List<Usuario> idRol(Integer idRol);
}
