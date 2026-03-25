package com.skillup.skillup.repository;


import com.skillup.skillup.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    boolean existsByNombre(String nombre);
    Optional<Curso> findByNombre(String nombre);
    @Query("SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.modulos")
    List<Curso> findAllWithModulos();



    @Query("SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.modulos m LEFT JOIN FETCH m.contenidos WHERE c.id = :id")
    Optional<Curso> findByIdWithModulosAndContenidos(@Param("id") Integer id);

    @Query(value =
            "SELECT c.NOMBRE_CURSO as nombreCurso, " +
                    "       COALESCE(COUNT(i.ID_INSCRIPCION), 0) as totalInscripciones " +
                    "FROM CURSOS c " +
                    "LEFT JOIN inscripciones i ON c.ID_CURSOS = i.ID_CURSOS " +
                    "GROUP BY c.ID_CURSOS, c.NOMBRE_CURSO " +
                    "ORDER BY c.NOMBRE_CURSO",
            nativeQuery = true)
    List<Object[]> obtenerReporteCursosNativo();

    @Query(value = "SELECT COUNT(*) FROM CURSOS", nativeQuery = true)
    Integer contarTotalCursos();

    @Query(value = "SELECT COUNT(*) FROM inscripciones", nativeQuery = true)
    Integer contarTotalInscripciones();

}