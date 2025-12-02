package com.skillup.skillup.controller;

import com.skillup.skillup.service.InscripcionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiante/inscripcion")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping("/inscribirse/{idCurso}")
    public String inscribirse(@PathVariable Integer idCurso, HttpSession session, Model model) {

        String identificacion = (String) session.getAttribute("roles_sistema");

        inscripcionService.inscribirEstudiante(idCurso, identificacion);

        model.addAttribute("mensaje", "¡Te has inscrito correctamente!");
        return "estudiante/inscripcionexitosa";
    }

}

