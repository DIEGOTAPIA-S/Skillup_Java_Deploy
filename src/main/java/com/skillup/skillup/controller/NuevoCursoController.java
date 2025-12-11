package com.skillup.skillup.controller;

import com.skillup.skillup.Dto.CursoDTO;
import com.skillup.skillup.model.Curso;
import com.skillup.skillup.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cursos")
public class NuevoCursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> guardarCurso(
            @Valid @RequestBody CursoDTO cursoDTO,
            BindingResult bindingResult) {

        Map<String, Object> response = new HashMap<>();

        // Validar errores de validación
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Error de validación"
                    ));

            response.put("success", false);
            response.put("message", "Errores de validación");
            response.put("errores", errores);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Guardar el curso
            Curso cursoGuardado = cursoService.guardarCursoCompleto(cursoDTO);

            response.put("success", true);
            response.put("message", "Curso creado exitosamente");
            response.put("cursoId", cursoGuardado.getId());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error al crear el curso: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}