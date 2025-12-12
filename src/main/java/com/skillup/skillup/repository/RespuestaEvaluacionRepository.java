package com.skillup.skillup.repository;

import com.skillup.skillup.model.RespuestaEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaEvaluacionRepository extends JpaRepository<RespuestaEvaluacion, Integer> {

    List<RespuestaEvaluacion> findByEvaluacion_Id(Integer idEvaluacion);
}