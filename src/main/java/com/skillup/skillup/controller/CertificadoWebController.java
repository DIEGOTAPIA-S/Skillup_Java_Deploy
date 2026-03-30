package com.skillup.skillup.controller;

import com.skillup.skillup.model.Evaluacion;
import com.skillup.skillup.model.Usuario;
import com.skillup.skillup.repository.EvaluacionRepository;
import com.skillup.skillup.repository.UsuariosRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@RequestMapping("/estudiante/certificado")
public class CertificadoWebController {

    private final EvaluacionRepository evaluacionRepository;
    private final UsuariosRepository usuariosRepository;

    public CertificadoWebController(EvaluacionRepository evaluacionRepository, UsuariosRepository usuariosRepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @GetMapping("/ver/{id}")
    public String verCertificado(@PathVariable Integer id, Model model) {
        Evaluacion eval = evaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

        Usuario student = usuariosRepository.findById(eval.getIdUsuario().toString())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        String nombreCompleto = (student.getNombre() + " " + student.getApellido1() + " " + (student.getApellido2() != null ? student.getApellido2() : "")).toUpperCase();
        String nombreCurso = eval.getCurso().getNombre().toUpperCase();
        
        LocalDateTime fecha = eval.getFechaRevision() != null ? eval.getFechaRevision() : eval.getFechaEvaluacion();
        if (fecha == null) fecha = LocalDateTime.now();
        
        String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es-ES")));

        model.addAttribute("nombreEstudiante", nombreCompleto);
        model.addAttribute("nombreCurso", nombreCurso);
        model.addAttribute("fechaCertificado", fechaFormateada);

        return "estudiante/certificado-view";
    }
}
