package com.skillup.skillup.controller;

import com.skillup.skillup.model.Curso;
import com.skillup.skillup.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estudiante")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepo;

    // LISTA DE CURSOS
    @GetMapping("/cursos")
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoRepo.findAll());
        return "estudiante/cursos";
    }

    @GetMapping("/cursos/{idCurso}")
    public String verCurso(@PathVariable Integer idCurso, Model model) {
        model.addAttribute("curso", cursoRepo.findById(idCurso).orElseThrow());
        return "estudiante/comunicacion";
    }
}
