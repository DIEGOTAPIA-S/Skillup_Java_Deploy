package com.skillup.skillup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstudianteController {


    @GetMapping("/estudiante/certificacioncursos")
    public String certificacioncursos() {
        return "estudiante/certificacioncursos";

    }


    @GetMapping("/estudiante/comunicacion")
    public String comunicacion() {
        return "estudiante/comunicacion";

    }
}
