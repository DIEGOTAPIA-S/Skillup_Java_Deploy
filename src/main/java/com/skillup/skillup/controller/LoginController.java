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

    @GetMapping("/reset-jorge")
    @org.springframework.web.bind.annotation.ResponseBody
    public String resetJorgeMfa() {
        com.skillup.skillup.model.Usuario usuario = registrarseRepository.findByCorreo("jorge.s@skillup.com");
        if (usuario != null) {
            usuario.setMfaSecret(null);
            registrarseRepository.save(usuario);
            return "Éxito: MFA de jorge.s@skillup.com reseteado. Ya puedes loguearte y te pedirá configurar el Autenticador de nuevo.";
        }
        return "Error: No se encontró al usuario.";
    }

}
