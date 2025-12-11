package com.skillup.skillup.controller;

import com.skillup.skillup.model.Curso;
import com.skillup.skillup.repository.CursoRepository;
import com.skillup.skillup.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private CursoService cursoService;

    // LISTA DE CURSOS
    @GetMapping("/cursos")
    public String listarCursos(Model model) {
        List<Curso> cursos = cursoRepo.findAll();
        model.addAttribute("cursos", cursos);
        return "estudiante/cursos";
    }

    @GetMapping("/cursos/{idCurso}")
    public String verCurso(@PathVariable Integer idCurso, Model model) {
        Curso curso = cursoService.obtenerCursoConModulos(idCurso);
        model.addAttribute("curso", curso);
        return "estudiante/curso-detalle";
    }
}
