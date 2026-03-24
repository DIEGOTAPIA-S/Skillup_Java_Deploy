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

    // Búsqueda para filtros
    List<Usuario> findByIdRolAndIdentificacionContaining(Integer idRol, String identificacion);
    
    List<Usuario> findByIdRolAndNombreContainingIgnoreCase(Integer idRol, String nombre);

    List<Usuario> findByIdRolAndIdentificacionContainingAndNombreContainingIgnoreCase(Integer idRol, String identificacion, String nombre);

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByResetPasswordToken(String token);
}
