package com.skillup.skillup.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CurriculumSeccionRepository<CurriculumSeccion> extends JpaRepository<CurriculumSeccion, Long> {
    Optional<CurriculumSeccion> findByUsuarioIdAndSeccion(Long usuarioId, String seccion);
    List<CurriculumSeccion> findByUsuarioId(Long usuarioId);
}
