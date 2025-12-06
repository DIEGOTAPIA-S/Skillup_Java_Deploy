package com.skillup.skillup.repository;


import com.skillup.skillup.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer>, JpaSpecificationExecutor<Inscripcion> {

    boolean existsByCurso_IdAndUsuario_Identificacion(Integer idCurso, String identificacion);


}
