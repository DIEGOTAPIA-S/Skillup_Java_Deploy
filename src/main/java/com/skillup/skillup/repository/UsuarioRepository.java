package com.skillup.skillup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skillup.skillup.model.Usuario;
public  interface UsuarioRepository extends JpaRepository<Usuario, Long>{
}