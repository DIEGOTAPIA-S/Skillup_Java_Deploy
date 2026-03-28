package com.skillup.skillup.controller;

import com.skillup.skillup.model.Evaluacion;
import com.skillup.skillup.model.Inscripcion;
import com.skillup.skillup.model.Usuario;
import com.skillup.skillup.repository.EvaluacionRepository;
import com.skillup.skillup.repository.InscripcionRepository;
import com.skillup.skillup.repository.UsuariosRepository;
import com.skillup.skillup.service.CertificadoService;
import com.skillup.skillup.service.ProgresoModuloService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/evaluador")
public class EvaluadorController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private CertificadoService certificadoService;

    @GetMapping("/inicio")
    public String inicio(HttpSession session, Model model) {
        // Obtener el ID del evaluador desde la sesión
        String evaluadorId = (String) session.getAttribute("roles_sistema");

        // Verificar que esté autenticado
        if (evaluadorId == null) {
            return "redirect:/login";
        }

        // Obtener datos del evaluador para mostrar en la vista
        Optional<Usuario> evaluador = usuariosRepository.findById(evaluadorId);
        if (evaluador.isPresent()) {
            model.addAttribute("nombreEvaluador",
                    evaluador.get().getNombre() + " " + evaluador.get().getApellido1());
        }


        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        model.addAttribute("nombreUsuario", nombreUsuario);

        return "evaluador/inicio";
    }


    @GetMapping("/listaestudiantes")
    public String listaEstudiantes(HttpSession session, Model model) {
        // Verificar que esté autenticado
        String evaluadorId = (String) session.getAttribute("roles_sistema");

        if (evaluadorId == null) {
            return "redirect:/login";
        }

        // Obtener todos los estudiantes (rol 2)
        List<Usuario> estudiantes = usuariosRepository.findByIdRol(2);
        model.addAttribute("estudiantes", estudiantes);

        return "evaluador/listaestudiantes";
    }

    @GetMapping("/listaestudiantescertificados")
    public String listaEstudiantesCertificados(
            @RequestParam(required = false) String search,
            HttpSession session,
            Model model) {

        String evaluadorId = (String) session.getAttribute("roles_sistema");

        if (evaluadorId == null) {
            return "redirect:/login";
        }

        model.addAttribute("datos", certificadoService.obtenerCertificados(search));
        model.addAttribute("search", search);

        return "evaluador/otorgarCertificados";
    }


    @PostMapping("/aprobar/{idInscripcion}")
    public String aprobarCertificado(
            @PathVariable Integer idInscripcion,
            HttpSession session,
            RedirectAttributes ra) {

        String evaluadorId = (String) session.getAttribute("roles_sistema");

        if (evaluadorId == null) {
            return "redirect:/login";
        }

        try {
            String nombre = certificadoService.aprobarCertificado(idInscripcion, evaluadorId);

            ra.addFlashAttribute("mensaje",
                    "¡Certificado otorgado exitosamente para " + nombre + "!");

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al aprobar: " + e.getMessage());
        }

        return "redirect:/evaluador/listaestudiantescertificados";
    }

}