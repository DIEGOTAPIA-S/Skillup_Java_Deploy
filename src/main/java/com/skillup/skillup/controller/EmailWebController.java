package com.skillup.skillup.controller;

import com.skillup.skillup.Dto.NotificacionRequest;
import com.skillup.skillup.Dto.Response.ApiResponse;
import com.skillup.skillup.service.NotificacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/correo")
public class EmailWebController {

    private final NotificacionService notificacionService;

    public EmailWebController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }


    @GetMapping("/enviar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("notification", new NotificacionRequest());
        return "correo-form";
    }


    @PostMapping("/enviar")
    public String enviarCorreoDesdeFormulario(
            @ModelAttribute("notification") NotificacionRequest request,
            Model model
    ) {
        ApiResponse<String> response = notificacionService.sendNotificationToAllClients(request);

        model.addAttribute("resultado", response.getMessage());
        return "correo-form";
    }
}