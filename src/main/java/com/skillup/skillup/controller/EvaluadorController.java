package com.skillup.skillup.controller;

import com.skillup.skillup.model.Evaluacion;
import com.skillup.skillup.model.Inscripcion;
import com.skillup.skillup.model.Usuario;
import com.skillup.skillup.repository.EvaluacionRepository;
import com.skillup.skillup.repository.InscripcionRepository;
import com.skillup.skillup.repository.UsuariosRepository;
import com.skillup.skillup.service.ProgresoModuloService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/evaluador")
public class EvaluadorController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private ProgresoModuloService progresoService;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @GetMapping("/inicio")
    public String inicio(HttpSession session, Model model) {
        String evaluadorId = (String) session.getAttribute("roles_sistema");

        if (evaluadorId == null) {
            return "redirect:/login";
        }

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
    public String listaEstudiantes(
            @RequestParam(required = false) String search,
            HttpSession session, 
            Model model) {
        String evaluadorId = (String) session.getAttribute("roles_sistema");
        if (evaluadorId == null) {
            return "redirect:/login";
        }

        List<Inscripcion> inscripciones = inscripcionRepository.findAll();
        List<Map<String, Object>> datosEnriquecidos = new ArrayList<>();

        for (Inscripcion ins : inscripciones) {
            String nombreCompleto = (ins.getUsuario().getNombre() + " " + ins.getUsuario().getApellido1() + " " + (ins.getUsuario().getApellido2() != null ? ins.getUsuario().getApellido2() : "")).toLowerCase();
            String identificacion = ins.getUsuario().getIdentificacion().toLowerCase();

            // Filtrar si hay búsqueda
            if (search != null && !search.isEmpty()) {
                String s = search.toLowerCase();
                if (!nombreCompleto.contains(s) && !identificacion.contains(s)) {
                    continue;
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("inscripcion", ins);
            
            Integer idUsuario = Integer.parseInt(ins.getUsuario().getIdentificacion());
            Integer idCurso = ins.getCurso().getId();
            
            boolean terminada = progresoService.puedeHacerEvaluacion(idUsuario, idCurso);
            map.put("modulosTerminados", terminada);

            Optional<Evaluacion> evalOpt = evaluacionRepository.findByIdUsuarioAndCurso_Id(idUsuario, idCurso);
            
            String estadoLabel = "Sin Certificado";
            if (evalOpt.isPresent()) {
                String estado = evalOpt.get().getEstado();
                if ("APROBADA".equals(estado)) {
                    estadoLabel = "Certificado Generado";
                } else if ("PENDIENTE".equals(estado)) {
                    estadoLabel = "Pendiente Revisión";
                } else if ("REPROBADA".equals(estado)) {
                    estadoLabel = "Reprobado";
                }
            }
            map.put("estadoLabel", estadoLabel);
            datosEnriquecidos.add(map);
        }

        model.addAttribute("datos", datosEnriquecidos);
        model.addAttribute("search", search);
        return "evaluador/listaestudiantes";
    }

    @PostMapping("/aprobar/{idInscripcion}")
    public String aprobarCertificado(@PathVariable Integer idInscripcion, HttpSession session, RedirectAttributes ra) {
        String evaluadorId = (String) session.getAttribute("roles_sistema");
        if (evaluadorId == null) {
            return "redirect:/login";
        }

        try {
            Inscripcion ins = inscripcionRepository.findById(idInscripcion)
                    .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

            Integer idUsuario = Integer.parseInt(ins.getUsuario().getIdentificacion());
            Integer idCurso = ins.getCurso().getId();

            // Buscar si ya existe o crear una nueva
            Evaluacion eval = evaluacionRepository.findByIdUsuarioAndCurso_Id(idUsuario, idCurso)
                    .orElse(new Evaluacion());

            eval.setIdUsuario(idUsuario);
            eval.setCurso(ins.getCurso());
            eval.setEstado("APROBADA");
            eval.setFechaEvaluacion(LocalDateTime.now());
            eval.setFechaRevision(LocalDateTime.now());
            eval.setIdEvaluador(evaluadorId);
            eval.setPuntajeObtenido(BigDecimal.valueOf(100));
            eval.setPuntajeTotal(BigDecimal.valueOf(100));
            eval.setPorcentaje(BigDecimal.valueOf(100));
            eval.setComentarios("Certificado otorgado manualmente por el evaluador.");

            evaluacionRepository.save(eval);

            ra.addFlashAttribute("mensaje", "¡Certificado otorgado exitosamente para " + ins.getUsuario().getNombre() + "!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al aprobar: " + e.getMessage());
        }

        return "redirect:/evaluador/listaestudiantes";
    }
}