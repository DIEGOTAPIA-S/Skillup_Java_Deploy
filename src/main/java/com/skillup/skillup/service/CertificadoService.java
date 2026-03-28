package com.skillup.skillup.service;

import com.skillup.skillup.Dto.CertificadoDTO;
import com.skillup.skillup.model.Evaluacion;
import com.skillup.skillup.model.Inscripcion;
import com.skillup.skillup.repository.EvaluacionRepository;
import com.skillup.skillup.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CertificadoService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private ProgresoModuloService progresoService;

    // Obtener lista de certificados con filtro de búsqueda
    public List<CertificadoDTO> obtenerCertificados(String search) {

        List<Inscripcion> inscripciones = inscripcionRepository.findAll();
        List<CertificadoDTO> resultado = new ArrayList<>();

        String filtro = (search != null) ? search.trim().toLowerCase() : "";

        for (Inscripcion ins : inscripciones) {

            if (ins == null) continue;

            Integer idUsuario = null;
            Integer idCurso = null;
            boolean modulosTerminados = false;
            String estadoLabel = "Sin información";

            String nombreCompleto = "";
            String identificacion = "";

            if (ins.getUsuario() != null && ins.getCurso() != null) {

                try {
                    //  DATOS PARA FILTRO
                    nombreCompleto = (
                            (ins.getUsuario().getNombre() != null ? ins.getUsuario().getNombre() : "") + " " +
                                    (ins.getUsuario().getApellido1() != null ? ins.getUsuario().getApellido1() : "") + " " +
                                    (ins.getUsuario().getApellido2() != null ? ins.getUsuario().getApellido2() : "")
                    ).toLowerCase();

                    identificacion = ins.getUsuario().getIdentificacion() != null
                            ? ins.getUsuario().getIdentificacion().toLowerCase()
                            : "";

                    // APLICAR FILTRO
                    if (!filtro.isEmpty()) {
                        if (!nombreCompleto.contains(filtro) && !identificacion.contains(filtro)) {
                            continue;
                        }
                    }

                    // LÓGICA NORMAL
                    idUsuario = Integer.parseInt(ins.getUsuario().getIdentificacion());
                    idCurso = ins.getCurso().getId();

                    modulosTerminados = progresoService.puedeHacerEvaluacion(idUsuario, idCurso);

                    Optional<Evaluacion> evalOpt =
                            evaluacionRepository.findByIdUsuarioAndCurso_Id(idUsuario, idCurso);

                    estadoLabel = construirEstado(evalOpt);

                } catch (Exception e) {
                    estadoLabel = "Error en datos";
                }
            }

            resultado.add(new CertificadoDTO(ins, modulosTerminados, estadoLabel));
        }

        return resultado;
    }

   // APRBAR CERTIFICADOS
    public String aprobarCertificado(Integer idInscripcion, String evaluadorId) {

        Inscripcion ins = inscripcionRepository.findById(idInscripcion)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        Integer idUsuario = Integer.parseInt(ins.getUsuario().getIdentificacion());
        Integer idCurso = ins.getCurso().getId();

        //  Validación importante
        boolean puedeEvaluar = progresoService.puedeHacerEvaluacion(idUsuario, idCurso);
        if (!puedeEvaluar) {
            throw new RuntimeException("El estudiante no ha completado los módulos");
        }

        Evaluacion eval = evaluacionRepository
                .findByIdUsuarioAndCurso_Id(idUsuario, idCurso)
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

        return ins.getUsuario().getNombre();
    }

    // CONSTRUIR ESTADO DE CERTIFICADO
    private String construirEstado(Optional<Evaluacion> evalOpt) {

        if (evalOpt.isEmpty()) {
            return "Sin Certificado";
        }

        return switch (evalOpt.get().getEstado()) {
            case "APROBADA" -> "Certificado Generado";
            case "PENDIENTE" -> "Pendiente Revisión";
            case "REPROBADA" -> "Reprobado";
            default -> "Desconocido";
        };
    }
}