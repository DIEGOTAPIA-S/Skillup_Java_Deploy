package com.skillup.skillup.repository;

import com.skillup.skillup.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrarseRepository extends JpaRepository<Usuario, String> {

    boolean existsByIdentificacion(String identificacion);

    boolean existsByCorreo(String correo);

    Usuario findByIdentificacion(String identificacion);

    Usuario findByCorreo(String correo);
}