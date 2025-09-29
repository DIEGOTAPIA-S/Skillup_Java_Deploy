/*package com.skillup.skillup.service.impl;

import java.util.List;
import java.util.Optional;

import com.skillup.skillup.model.CurriculumSeccion;
import com.skillup.skillup.repository.CurriculumSeccionRepository;
import com.skillup.skillup.repository.SeccionRepository;
import com.skillup.skillup.service.SeccionService;
import org.apache.xmlbeans.impl.store.Cur;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository seccionRepository;

    public SeccionServiceImpl(SeccionRepository seccionRepository) {
        this.seccionRepository = seccionRepository;
    }

    @Override
    public CurriculumSeccion crearSeccion(CurriculumSeccion seccion) {
        seccion.setCreadoEn(LocalDateTime.now());
        seccion.setActualizadoEn(LocalDateTime.now());
        return seccionRepository.save(seccion);
    }

    @Override
    public CurriculumSeccion actualizarSeccion(Long id, CurriculumSeccion seccion) {
        return seccionRepository.findById(id).map(s -> {
            s.setSeccion(seccion.getSeccion());
            s.setContenido(seccion.getContenido());
            s.setActualizadoEn(LocalDateTime.now());
            return seccionRepository.save(s);
        }).orElseThrow(() -> new RuntimeException("Sección no encontrada con id " + id));
    }

    @Override
    public void eliminarSeccion(Long id) {
        if (!seccionRepository.existsById(id)) {
            throw new RuntimeException("Sección no encontrada con id " + id);
        }
        seccionRepository.deleteById(id);
    }

    @Override
    public Optional<CurriculumSeccion> obtenerPorId(Long id) {
        return seccionRepository.findById(id);
    }

    @Override
    public List<CurriculumSeccion> obtenerTodas() {
        return seccionRepository.findAll();
    }

    @Override
    public List<CurriculumSeccion> obtenerPorUsuario(Long usuarioId) {
        return seccionRepository.findByUsuario_Id(usuarioId);
    }
}
*/