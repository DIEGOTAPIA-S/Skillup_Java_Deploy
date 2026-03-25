package com.skillup.skillup.repository;

import com.skillup.skillup.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, String> {

    List<Usuario> findByIdRol(Integer idRol);

    Optional<Usuario> findByIdentificacion(String identificacion);

    @Query("SELECT u FROM Usuario u WHERE u.idRol = :idRol AND " +
           "(:identificacion IS NULL OR u.identificacion LIKE CONCAT('%', :identificacion, '%')) AND " +
           "(:nombre IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Usuario> buscarUsuarios(@Param("idRol") Integer idRol, 
                                @Param("identificacion") String identificacion, 
                                @Param("nombre") String nombre);

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByResetPasswordToken(String token);
}
