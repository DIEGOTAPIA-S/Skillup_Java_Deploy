package com.skillup.skillup.controller;

import com.skillup.skillup.model.Rol;
import com.skillup.skillup.model.Curso;
import com.skillup.skillup.model.Seccion;
import com.skillup.skillup.repository.RolRepository;
import com.skillup.skillup.repository.CursoRepository;
import com.skillup.skillup.repository.SeccionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MiCuentaController {

    private final RolRepository rolRepository;
    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepository;

    public MiCuentaController(RolRepository rolRepository, CursoRepository cursoRepository, SeccionRepository seccionRepository) {
        this.rolRepository = rolRepository;
        this.cursoRepository = cursoRepository;
        this.seccionRepository = seccionRepository;
    }

    @GetMapping("/usuarios/mi_cuenta")
    public String index(HttpSession session, Model model) {
        Long idUsuario = (Long) session.getAttribute("roles_sistema");

        if (idUsuario == null) {
            return "redirect:/login";
        }

        Rol usuario = rolRepository.findById(idUsuario).orElse(null);
        List<Curso> cursos = cursoRepository.findByEstudianteId(idUsuario);

        // Traer solo Educación y Certificaciones
        Map<String, String> contenidoSecciones = new HashMap<>();
        String[] estudios = {"Educación", "Certificaciones"};

        for (String titulo : estudios) {
            curriculumSeccionRepository
                    .findByUsuarioIdAndTitulo(idUsuario, titulo)
                    .ifPresent(seccion -> contenidoSecciones.put(titulo, seccion.getContenido()));
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("cursos", cursos);
        model.addAttribute("contenido_secciones", contenidoSecciones);

        return "usuarios/mi_cuenta";
    }

    @GetMapping("/usuarios/mi_cuenta")
    public String index(HttpSession session, Model model) {
        Long idUsuario = (Long) session.getAttribute("roles_sistema");

        if (idUsuario == null) {
            return "redirect:/login";
        }

        Rol usuario = rolRepository.findById(idUsuario).orElse(null);
        List<Curso> cursos = cursoRepository.findByEstudianteId(idUsuario);

        // Solo traemos Educación y Certificaciones del currículum
        Map<String, String> contenidoSecciones = new HashMap<>();
        String[] estudios = {"Educación", "Certificaciones"};

        for (String titulo : estudios) {
            curriculumSeccionRepository
                    .findByUsuarioAndTitulo(idUsuario.toString(), titulo)
                    .ifPresent(seccion -> contenidoSecciones.put(titulo, seccion.getContenido()));
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("cursos", cursos);
        model.addAttribute("contenido_secciones", contenidoSecciones);

        return "usuarios/mi_cuenta";
    }

    // Guardar sección
    @PostMapping("/usuarios/micuenta/guardar")
    public String guardarSeccion(HttpSession session,
            @RequestParam String seccion,
            @RequestParam String contenido) {
        Long idUsuario = (Long) session.getAttribute("roles_sistema");
        if (idUsuario == null) {
            return "redirect:/login";
        }

        // Verificar si ya existe la sección para este usuario
        Seccion existente = seccionRepository.findByUsuarioIdAndSeccion(idUsuario, seccion);
        if (existente != null) {
            existente.setContenido(contenido);
            seccionRepository.save(existente);
        } else {
            Seccion nueva = new Seccion();
            nueva.setUsuarioId(idUsuario);
            nueva.setSeccion(seccion);
            nueva.setContenido(contenido);
            seccionRepository.save(nueva);
        }

        return "redirect:/usuarios/mi_cuenta";
    }

    // Eliminar sección
    @PostMapping("/usuarios/micuenta/eliminar")
    public String eliminarSeccion(HttpSession session,
            @RequestParam String seccion) {
        Long idUsuario = (Long) session.getAttribute("roles_sistema");
        if (idUsuario == null) {
            return "redirect:/login";
        }

        Seccion existente = seccionRepository.findByUsuarioIdAndSeccion(idUsuario, seccion);
        if (existente != null) {
            seccionRepository.delete(existente);
        }

        return "redirect:/usuarios/mi_cuenta";
    }
}
