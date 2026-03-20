package com.skillup.skillup.controller;

import com.skillup.skillup.model.Inscripcion;
import com.skillup.skillup.repository.InscripcionRepository;
import com.skillup.skillup.service.InscripcionService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class CursosEvaluadorController {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping("/evaluador/cursosEval")
    public String index(Model model) {
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();
        model.addAttribute("inscripciones", inscripciones);
        return "evaluador/cursosEval";
    }

    @PostMapping("/cursoseval/filtrar")
    public String filtrarInscripciones(
            @RequestParam(name = "nombre", required = false) String nombreCurso,
            @RequestParam(name = "estudiante", required = false) String nombreUsuario,
            @RequestParam(name = "identificacion", required = false) String identificacion,
            Model model
    ) {
        List<Inscripcion> inscripcionesFiltradas = inscripcionService.filtrarInscripciones(
                nombreCurso, nombreUsuario, identificacion
        );

        model.addAttribute("inscripciones", inscripcionesFiltradas);
        model.addAttribute("oldNombre", nombreCurso);
        model.addAttribute("oldEstudiante", nombreUsuario);
        model.addAttribute("oldIdentificacion", identificacion);

        return "evaluador/cursosEval";
    }

    @GetMapping("/cursoseval/exportar")
    public void exportarExcel(
            @RequestParam(name = "nombre", required = false) String nombreCurso,
            @RequestParam(name = "estudiante", required = false) String nombreUsuario,
            @RequestParam(name = "identificacion", required = false) String identificacion,
            HttpServletResponse response) throws IOException {

        List<Inscripcion> inscripciones = inscripcionService.filtrarInscripciones(
                nombreCurso, nombreUsuario, identificacion
        );

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inscripciones");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Nombre del Curso");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Apellido");
        header.createCell(3).setCellValue("Identificación");
        header.createCell(4).setCellValue("Estado");

        int rowNum = 1;
        for (Inscripcion inscripcion : inscripciones) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(
                    inscripcion.getCurso() != null && inscripcion.getCurso().getNombre() != null
                            ? inscripcion.getCurso().getNombre()
                            : ""
            );

            if (inscripcion.getUsuario() != null) {
                row.createCell(1).setCellValue(
                        inscripcion.getUsuario().getNombre() != null
                                ? inscripcion.getUsuario().getNombre()
                                : ""
                );
                row.createCell(2).setCellValue(
                        inscripcion.getUsuario().getApellido1() != null
                                ? inscripcion.getUsuario().getApellido1()
                                : ""
                );
                row.createCell(3).setCellValue(
                        inscripcion.getUsuario().getIdentificacion() != null
                                ? inscripcion.getUsuario().getIdentificacion()
                                : ""
                );
            } else {
                row.createCell(1).setCellValue("");
                row.createCell(2).setCellValue("");
                row.createCell(3).setCellValue("");
            }

            row.createCell(4).setCellValue(
                    inscripcion.getEstado() != null
                            ? inscripcion.getEstado()
                            : ""
            );
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String filename = "Inscripciones_Filtradas_" + System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}