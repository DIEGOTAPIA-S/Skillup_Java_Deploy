package com.skillup.skillup.repository;

import com.skillup.skillup.model.Usuario;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCorreoAndContrasena(String correo, String contrasena);
}