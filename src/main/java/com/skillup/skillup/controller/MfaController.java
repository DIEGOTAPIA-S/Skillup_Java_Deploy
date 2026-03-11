package com.skillup.skillup.controller;

import com.skillup.skillup.model.Usuarios;
import com.skillup.skillup.repository.LoginRepository;
import com.skillup.skillup.service.MfaService;
import dev.samstevens.totp.exceptions.QrGenerationException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mfa")
public class MfaController {

    @Autowired
    private MfaService mfaService;

    @Autowired
    private LoginRepository loginRepository;

    /**
     * Paso 1: Configuración inicial (mostrar QR)
     */
    @GetMapping("/configurar")
    public String mostrarConfiguracion(HttpSession session, Model model) throws QrGenerationException {
        String correo = (String) session.getAttribute("mfa_correo_pendiente");
        if (correo == null) return "redirect:/login";

        Usuarios usuario = loginRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar nuevo secreto si no tiene
        String secret = usuario.getMfaSecret();
        if (secret == null || secret.isEmpty()) {
            secret = mfaService.generateSecret();
            usuario.setMfaSecret(secret);
            loginRepository.save(usuario);
        }

        String qrUri = mfaService.generateQrImageUri(secret, correo);
        model.addAttribute("qrUri", qrUri);
        model.addAttribute("secret", secret);

        return "mfa-configurar";
    }

    /**
     * Paso 2: Pantalla de verificación (pedir código de 6 dígitos)
     */
    @GetMapping("/verificar")
    public String mostrarVerificacion(HttpSession session) {
        if (session.getAttribute("mfa_correo_pendiente") == null) {
            return "redirect:/login";
        }
        return "mfa-verificar";
    }

    /**
     * Procesar la validación del código
     */
    @PostMapping("/validar")
    public String validarCodigo(@RequestParam String codigo, HttpSession session, Model model) {
        String correo = (String) session.getAttribute("mfa_correo_pendiente");
        if (correo == null) return "redirect:/login";

        Usuarios usuario = loginRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (mfaService.isCodeValid(usuario.getMfaSecret(), codigo)) {
            // Código correcto: Limpiar estado temporal y permitir entrada
            session.removeAttribute("mfa_correo_pendiente");
            session.setAttribute("mfa_autenticado", true);

            // Redirigir según el rol guardado previamente en sesión
            String rol = (String) session.getAttribute("mfa_rol_pendiente");
            switch (rol) {
                case "ROLE_1": return "redirect:/administrador/seleccionar";
                case "ROLE_2": return "redirect:/estudiante/lobby";
                case "ROLE_3": return "redirect:/evaluador/inicio";
                default: return "redirect:/login";
            }
        } else {
            model.addAttribute("error", "Código incorrecto. Intente nuevamente.");
            return "mfa-verificar";
        }
    }
}
