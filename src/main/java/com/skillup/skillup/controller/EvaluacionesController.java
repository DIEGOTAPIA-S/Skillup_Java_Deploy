package com.skillup.skillup.controller;

import com.skillup.skillup.Dto.EvaluacionFormDTO;
import com.skillup.skillup.model.PreguntaEvaluacion;
import com.skillup.skillup.service.CursoService;
import com.skillup.skillup.service.EvaluacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class EvaluacionesController {

    private final CursoService cursoService;
    private final EvaluacionService evaluacionService;

    public EvaluacionesController(CursoService cursoService, EvaluacionService evaluacionService) {
        this.cursoService = cursoService;
        this.evaluacionService = evaluacionService;
    }

    @GetMapping("/evaluador/formularioevaluacion")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cursos", cursoService.obtenerTodos());
        return "evaluador/formularioevaluacion"; // nombre del HTML
    }

    @PostMapping("/evaluador/crearEvaluacion")
    public String crearEvaluacion(EvaluacionFormDTO formDTO) {
        evaluacionService.crearEvaluacion(formDTO);
        return "redirect:/evaluador/misevaluaciones";
    }

    @GetMapping("/evaluador/misevaluaciones")
    public String verMisEvaluaciones(Model model) {
        List<PreguntaEvaluacion> preguntas = evaluacionService.obtenerTodasLasPreguntas();

        Map<String, List<PreguntaEvaluacion>> agrupadas = preguntas.stream()
                .collect(Collectors.groupingBy(p ->
                        p.getCurso() != null ? p.getCurso() .getNombre() : "Sin curso"));


        model.addAttribute("evaluacionesPorCurso", agrupadas);


        return "evaluador/misevaluaciones";
    }

}