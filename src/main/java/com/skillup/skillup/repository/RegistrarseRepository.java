package com.skillup.skillup.repository;

import com.skillup.skillup.model.Registrarse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrarseRepository extends JpaRepository<Registrarse, Long> {

    /**
     * Verifica si existe un usuario con la identificación dada
     */
    boolean existsByIdentificacion(String identificacion);

    /**
     * Verifica si existe un usuario con el correo dado
     */
    boolean existsByCorreo(String correo);

    /**
     * Busca un usuario por su identificación
     */
    Registrarse findByIdentificacion(String identificacion);

    /**
     * Busca un usuario por su correo
     */
    Registrarse findByCorreo(String correo);
}