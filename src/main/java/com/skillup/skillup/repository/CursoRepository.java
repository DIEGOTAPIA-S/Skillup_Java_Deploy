package com.skillup.skillup.repository;

import com.skillup.skillup.Dto.CursoReporteDto;
import com.skillup.skillup.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    boolean existsByNombre(String nombre);


    @Query(value =
            "SELECT c.NOMBRE_CURSO as nombreCurso, " +
                    "       COALESCE(COUNT(i.ID_INSCRIPCION), 0) as totalInscripciones " +
                    "FROM CURSOS c " +
                    "LEFT JOIN inscripciones i ON c.ID_CURSOS = i.ID_CURSOS " +  // ← Cambio aquí
                    "GROUP BY c.ID_CURSOS, c.NOMBRE_CURSO " +
                    "ORDER BY c.NOMBRE_CURSO",
            nativeQuery = true)
    List<Object[]> obtenerReporteCursosNativo();

    @Query(value = "SELECT COUNT(*) FROM CURSOS", nativeQuery = true)
    Integer contarTotalCursos();

    @Query(value = "SELECT COUNT(*) FROM inscripciones", nativeQuery = true)  // ← Cambio aquí
    Integer contarTotalInscripciones();

}