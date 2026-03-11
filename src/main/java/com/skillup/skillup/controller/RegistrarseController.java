package com.skillup.skillup.controller;


import com.skillup.skillup.Dto.RegistroDTO;
import com.skillup.skillup.service.RegistrarseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/registro")
public class RegistrarseController {

    @Autowired
    private RegistrarseService registrarseService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("registroDTO", new RegistroDTO());
        return "registro";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute RegistroDTO registroDTO,
                                 RedirectAttributes redirectAttributes,
                                 Locale locale) {

        System.out.println("RECIBIDA PETICIÓN DE REGISTRO PARA: " + registroDTO.getCorreo());
        // Validar los datos del DTO
        List<String> errores = registrarseService.validarRegistro(registroDTO);

        // Si hay errores, redirigir al formulario
        if (!errores.isEmpty()) {
            redirectAttributes.addFlashAttribute("errores", errores);
            redirectAttributes.addFlashAttribute("registroDTO", registroDTO);
            return "redirect:/registro";
        }

        try {
            // Guardar el usuario
            registrarseService.guardarUsuario(registroDTO);

            System.out.println("REGISTRO EXITOSO PARA: " + registroDTO.getCorreo() + ". REDIRIGIENDO A /login?exito");
            return "redirect:/login?exito";

        } catch (Exception e) {
            String mensajeError = messageSource.getMessage("error.registro.general", null, locale);
            errores.add(mensajeError + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("errores", errores);
            redirectAttributes.addFlashAttribute("registroDTO", registroDTO);
            return "redirect:/registro";
        }
    }
}