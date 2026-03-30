package com.skillup.skillup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class LoginController {

    @org.springframework.beans.factory.annotation.Autowired
    private com.skillup.skillup.repository.RegistrarseRepository registrarseRepository;

    @GetMapping
    public String index() {
        return "login";
    }

    @GetMapping("/reset-mfa")
    @org.springframework.web.bind.annotation.ResponseBody
    public String resetMfa(@org.springframework.web.bind.annotation.RequestParam String correo) {
        com.skillup.skillup.model.Usuario usuario = registrarseRepository.findByCorreo(correo);
        if (usuario != null) {
            usuario.setMfaSecret(null);
            registrarseRepository.save(usuario);
            return "Éxito: MFA de " + correo + " reseteado. Ya puedes loguearte y configurar el Autenticador de nuevo.";
        }
        return "Error: No se encontró al usuario con correo: " + correo;
    }

}
