package com.skillup.skillup.repository;

import com.skillup.skillup.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    List<Curso> findByUsuario_Identificacion(Integer identificacion);
}
