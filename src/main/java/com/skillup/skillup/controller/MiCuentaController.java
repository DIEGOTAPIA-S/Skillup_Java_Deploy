package com.skillup.skillup.controller;

import com.skillup.skillup.model.Curso;
import com.skillup.skillup.model.Rol;
import com.skillup.skillup.model.Usuarios;
import com.skillup.skillup.service.MiCuentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/micuenta")
public class MiCuentaController {

    private final MiCuentaService miCuentaService;

    public MiCuentaController(MiCuentaService miCuentaService) {
        this.miCuentaService = miCuentaService;
    }

    // Equivalente a index()
    @GetMapping
    public String index(HttpSession session, Model model) {
        Integer identificacion = (Integer) session.getAttribute("roles_sistema");
        if (identificacion == null) {
            return "redirect:/login";
        }

        Optional<Usuarios> optUsuario = miCuentaService.obtenerUsuario(identificacion);
        if (optUsuario.isEmpty()) {
            return "redirect:/login";
        }

        Usuarios usuario = optUsuario.get();
        Rol rol = usuario.getRol();
        List<Curso> cursos = miCuentaService.obtenerCursosPorEstudiante(identificacion);

        Map<String,String> secciones = miCuentaService.obtenerContenidoSecciones(identificacion, "Educación", "Certificaciones");

        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", rol);
        model.addAttribute("cursos", cursos);
        model.addAttribute("contenido_secciones", secciones);

        return "micuenta"; // tu vista Thymeleaf (me dijiste que luego me compartes las vistas)
    }

    // guardar() -> POST /micuenta/guardar
    @PostMapping("/guardar")
    public String guardarSeccion(HttpSession session,
                                 @RequestParam String seccion,
                                 @RequestParam String contenido,
                                 RedirectAttributes redirectAttributes) {
        Integer identificacion = (Integer) session.getAttribute("roles_sistema");
        if (identificacion == null) return "redirect:/login";

        miCuentaService.guardarSeccion(identificacion, seccion, contenido);
        redirectAttributes.addFlashAttribute("mensaje", "Sección '" + seccion + "' guardada correctamente.");
        return "redirect:/micuenta";
    }

    // eliminar() -> POST /micuenta/eliminar
    @PostMapping("/eliminar")
    public String eliminarSeccion(HttpSession session,
                                  @RequestParam String seccion,
                                  RedirectAttributes redirectAttributes) {
        Integer identificacion = (Integer) session.getAttribute("roles_sistema");
        if (identificacion == null) return "redirect:/login";

        miCuentaService.eliminarSeccion(identificacion, seccion);
        redirectAttributes.addFlashAttribute("mensaje", "Si existía, la sección fue eliminada.");
        return "redirect:/micuenta";
    }

    // editarPerfil() -> GET /micuenta/editar
    @GetMapping("/editar")
    public String editarPerfil(HttpSession session, Model model) {
        Integer identificacion = (Integer) session.getAttribute("roles_sistema");
        if (identificacion == null) return "redirect:/login";

        Optional<Usuarios> u = miCuentaService.obtenerUsuario(identificacion);
        if (u.isEmpty()) return "redirect:/login";

        model.addAttribute("usuario", u.get());
        return "editarPerfil"; // vista Thymeleaf
    }

    // guardarPerfil() -> POST /micuenta/guardarPerfil
    @PostMapping("/guardarPerfil")
    public String guardarPerfil(HttpSession session,
                                @RequestParam String NOMBRE,
                                @RequestParam String APELLIDO1,
                                @RequestParam(required = false) String APELLIDO2,
                                @RequestParam String CORREO,
                                RedirectAttributes redirectAttributes) {
        Integer identificacion = (Integer) session.getAttribute("roles_sistema");
        if (identificacion == null) return "redirect:/login";

        // Validaciones simples (puedes usar @Valid + DTO si prefieres)
        if (NOMBRE == null || NOMBRE.isBlank() || APELLIDO1 == null || APELLIDO1.isBlank() || CORREO == null || CORREO.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Por favor complete los campos obligatorios.");
            return "redirect:/micuenta/editar";
        }

        miCuentaService.actualizarPerfil(identificacion, NOMBRE, APELLIDO1, APELLIDO2, CORREO);
        redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado correctamente.");
        return "redirect:/micuenta";
    }

    // vistas simples para seccion_curriculum / seccion_estudios (GET)
    @GetMapping("/seccion_curriculum")
    public String seccionCurriculum() {
        return "seccion_curriculum";
    }

    @GetMapping("/seccion_estudios")
    public String seccionEstudios() {
        return "seccion_estudios";
    }
}
