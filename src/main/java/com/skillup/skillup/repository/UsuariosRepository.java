package com.skillup.skillup.repository;

import com.skillup.skillup.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, String> {

 List<Usuarios> findByIdRol(Integer idRol);

}
