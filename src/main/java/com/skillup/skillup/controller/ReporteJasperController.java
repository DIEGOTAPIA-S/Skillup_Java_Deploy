package com.skillup.skillup.controller;

import com.skillup.skillup.service.ReporteJasperService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
public class ReporteJasperController {

    private final ReporteJasperService reporteJasperService;

    public ReporteJasperController(ReporteJasperService reporteJasperService) {
        this.reporteJasperService = reporteJasperService;
    }

    @GetMapping("/cursos/pdf")
    public ResponseEntity<byte[]> descargarReporteCursos() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO", "Informe de Inscripciones por Curso");
        parametros.put("FECHA_REPORTE", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        byte[] pdfBytes = reporteJasperService.generarReporteCursosPdf(parametros);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String nombreArchivo = "reporte_cursos_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
        headers.setContentDispositionFormData("attachment", nombreArchivo);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/certificado/{id}")
    public ResponseEntity<byte[]> descargarCertificado(@PathVariable Integer id) {
        try {
            byte[] pdfBytes = reporteJasperService.generarCertificadoPdf(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Certificado_SkillUp.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}