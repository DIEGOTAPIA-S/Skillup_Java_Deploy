package com.skillup.skillup.repository;

import com.skillup.skillup.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    List<Evaluacion> findByIdUsuario(String idUsuario);

    List<Evaluacion> findByEstado(String estado);

    List<Evaluacion> findByCurso_Id(Integer idCurso);

    Optional<Evaluacion> findByIdUsuarioAndCurso_Id(String idUsuario, Integer idCurso);
}