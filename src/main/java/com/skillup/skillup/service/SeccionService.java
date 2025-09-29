package com.skillup.skillup.service;

import com.skillup.skillup.model.CurriculumSeccion;
import java.util.List;
import java.util.Optional;

public interface SeccionService {
    CurriculumSeccion crearSeccion(CurriculumSeccion seccion);
    CurriculumSeccion actualizarSeccion(Long id, CurriculumSeccion seccion);
    void eliminarSeccion(Long id);
    Optional<CurriculumSeccion> obtenerPorId(Long id);
    List<CurriculumSeccion> obtenerTodas();
    List<CurriculumSeccion> obtenerPorUsuario(Long usuarioId);
}
