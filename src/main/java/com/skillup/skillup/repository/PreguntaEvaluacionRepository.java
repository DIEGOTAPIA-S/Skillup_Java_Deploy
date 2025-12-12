package com.skillup.skillup.repository;

import com.skillup.skillup.model.Curso;
import com.skillup.skillup.model.PreguntaEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaEvaluacionRepository extends JpaRepository<PreguntaEvaluacion, Integer> {

    List<PreguntaEvaluacion> findByCurso(Curso curso);
}