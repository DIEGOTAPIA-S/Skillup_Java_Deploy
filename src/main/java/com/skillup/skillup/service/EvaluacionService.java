package com.skillup.skillup.service;

import com.skillup.skillup.Dto.EvaluacionFormDTO;
import com.skillup.skillup.model.*;
import com.skillup.skillup.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvaluacionService {

    private final CursoRepository cursoRepository;
    private final UsuariosRepository usuarioRepository;
    private final PreguntaEvaluacionRepository preguntaRepository;

    public EvaluacionService(CursoRepository cursoRepository,
                             UsuariosRepository usuarioRepository,
                             PreguntaEvaluacionRepository preguntaRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.preguntaRepository = preguntaRepository;
    }

    @Transactional
    public void crearEvaluacion(EvaluacionFormDTO formDTO) {

        Curso curso = cursoRepository.findById(formDTO.getIdCurso())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Solo iteramos sobre las preguntas, no sobre los estudiantes
        for (EvaluacionFormDTO.PreguntaDTO p : formDTO.getPreguntas()) {
            PreguntaEvaluacion pregunta = new PreguntaEvaluacion();
            pregunta.setCurso(curso);
            pregunta.setPregunta(p.getPregunta());
            pregunta.setOpcionA(p.getOpcionA());
            pregunta.setOpcionB(p.getOpcionB());
            pregunta.setOpcionC(p.getOpcionC());
            pregunta.setOpcionD(p.getOpcionD());
            pregunta.setRespuestaCorrecta(p.getRespuestaCorrecta());
            preguntaRepository.save(pregunta);
        }
    }

    @Transactional(readOnly = true)
    public List<PreguntaEvaluacion> obtenerTodasLasPreguntas() {
        return preguntaRepository.findAll();
    }

}
