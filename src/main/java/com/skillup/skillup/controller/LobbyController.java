package com.skillup.skillup.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LobbyController {

    @GetMapping("/estudiante/lobby")
    public ModelAndView lobbyEstudiante(HttpSession session) {

        Object rol = session.getAttribute("rol_sistema");
        Object rolesSistema = session.getAttribute("roles_sistema");

        if (rolesSistema == null || rol == null || !(rol.equals(2))) {

            return new ModelAndView("redirect:/login");
        }


        ModelAndView modelAndView = new ModelAndView("estudiante/lobby");
        return modelAndView;
    }
}

