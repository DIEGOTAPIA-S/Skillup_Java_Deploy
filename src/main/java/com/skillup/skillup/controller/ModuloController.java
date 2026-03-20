package com.skillup.skillup.controller;

import com.skillup.skillup.model.Modulo;
import com.skillup.skillup.service.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/administrador/modulos")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> crearModulo(
            @RequestParam Integer idCurso,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam(required = false) Integer orden) {

        Map<String, Object> response = new HashMap<>();
        try {
            moduloService.crearModulo(idCurso, nombre, descripcion, orden);
            response.put("success", true);
            response.put("message", "✅ Módulo creado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "❌ Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizarModulo(
            @PathVariable Integer id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam(required = false) Integer orden) {

        Map<String, Object> response = new HashMap<>();
        try {
            moduloService.actualizarModulo(id, nombre, descripcion, orden);
            response.put("success", true);
            response.put("message", "✅ Módulo actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "❌ Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> eliminarModulo(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            moduloService.eliminarModulo(id);
            response.put("success", true);
            response.put("message", "✅ Módulo eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "❌ Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
