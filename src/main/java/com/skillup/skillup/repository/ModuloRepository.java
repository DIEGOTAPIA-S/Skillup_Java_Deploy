package com.skillup.skillup.repository;

import com.skillup.skillup.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuloRepository extends JpaRepository<Modulo, Integer> {
    List<Modulo> findByCurso_IdOrderByOrdenAsc(Integer idCurso);

    long countByCurso_Id(Integer idCurso);
}