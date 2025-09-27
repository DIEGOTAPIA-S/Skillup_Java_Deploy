package com.skillup.skillup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CursosController {

    @GetMapping("/cursos")
    public String index() {
        // Retorna la plantilla en resources/templates/estudiante/cursos.html
        return "estudiante/cursos";
    }

    @GetMapping("/negociacion")
    public String negociacion() {
        return "estudiante/negociacion";
    }

    @GetMapping("/liderazgo")
    public String liderazgo() {
        return "estudiante/liderazgo";
    }

    @GetMapping("/trabajoequipo")
    public String trabajoEquipo() {
        return "estudiante/trabajoequipo";
    }

    @GetMapping("/comunicacion")
    public String comunicacion() {
        return "estudiante/comunicacion";
    }
}
