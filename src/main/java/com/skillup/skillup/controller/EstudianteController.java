package com.skillup.skillup.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class EstudianteController {


    @GetMapping("/estudiante/certificacioncursos")
    public ModelAndView certificacionCursos() {
        return new ModelAndView("estudiante/certificacioncursos");
    }


    @GetMapping("/estudiante/comunicacion")
    public ModelAndView comunicacion() {
        return new ModelAndView("estudiante/comunicacion");
    }


    @GetMapping("/estudiante/trabajoequipo")
    public ModelAndView trabajoEquipo() {
        return new ModelAndView("estudiante/trabajoequipo");
    }


    @GetMapping("/estudiante/negociacion")
    public ModelAndView negociacion() {
        return new ModelAndView("estudiante/negociacion");
    }


    @GetMapping("/estudiante/liderazgo")
    public ModelAndView liderazgo() {
        return new ModelAndView("estudiante/liderazgo");
    }



}