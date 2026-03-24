package com.skillup.skillup.controller;

import com.skillup.skillup.model.Usuario;
import com.skillup.skillup.repository.UsuariosRepository;
import com.skillup.skillup.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("correo") String correo, Model model) {
        Optional<Usuario> optionalUsuario = usuariosRepository.findByCorreo(correo);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            String token = UUID.randomUUID().toString();
            
            usuario.setResetPasswordToken(token);
            usuario.setResetPasswordTokenExpiry(LocalDateTime.now().plusHours(24));
            usuariosRepository.save(usuario);

            String resetLink = "https://skillup-java.onrender.com/reset-password?token=" + token;
            // Para local usar: "http://localhost:8080/reset-password?token=" + token;
            
            emailService.sendResetPasswordEmail(usuario.getCorreo(), resetLink);
            model.addAttribute("message", "📩 Se ha enviado un enlace de recuperación a tu correo.");
        } else {
            model.addAttribute("error", "❌ No se encontró ningún usuario con ese correo electrónico.");
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        Optional<Usuario> optionalUsuario = usuariosRepository.findByResetPasswordToken(token);

        if (optionalUsuario.isEmpty() || optionalUsuario.get().getResetPasswordTokenExpiry().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "❌ El enlace de recuperación es inválido o ha expirado.");
            return "forgot-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model) {
        Optional<Usuario> optionalUsuario = usuariosRepository.findByResetPasswordToken(token);

        if (optionalUsuario.isEmpty() || optionalUsuario.get().getResetPasswordTokenExpiry().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "❌ Hubo un problema al procesar tu solicitud.");
            return "forgot-password";
        }

        Usuario usuario = optionalUsuario.get();
        usuario.setContrasena(passwordEncoder.encode(password));
        usuario.setResetPasswordToken(null);
        usuario.setResetPasswordTokenExpiry(null);
        usuariosRepository.save(usuario);

        model.addAttribute("message", "✅ Tu contraseña ha sido actualizada exitosamente. Ya puedes iniciar sesión.");
        return "login";
    }
}
