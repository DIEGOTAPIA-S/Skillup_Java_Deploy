package com.skillup.skillup.controller;

import com.skillup.skillup.model.Contenido;
import com.skillup.skillup.model.Modulo;
import com.skillup.skillup.service.ContenidoService;
import com.skillup.skillup.service.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/administrador/contenidos")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    @Autowired
    private ModuloService moduloService;

    // Ver gestión de contenidos de un módulo
    @GetMapping("/modulo/{idModulo}")
    public String gestionarContenidos(@PathVariable Integer idModulo, Model model) {
        try {
            Modulo modulo = moduloService.obtenerModuloPorId(idModulo);
            List<Contenido> contenidos = contenidoService.obtenerContenidosPorModulo(idModulo);

            model.addAttribute("modulo", modulo);
            model.addAttribute("contenidos", contenidos);
            return "administrador/gestionarContenidos";

        } catch (Exception e) {
            System.err.println("ERROR en gestionarContenidos: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error: " + e.getMessage());
            return "redirect:/administrador/cursos/listar";
        }
    }

    @PostMapping("/crear")
    public String crearContenido(
            @RequestParam Integer idModulo,
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam(required = false) Integer orden,
            Model model) {

        try {
            contenidoService.crearContenido(idModulo, titulo, descripcion, orden);
            return "redirect:/administrador/contenidos/modulo/" + idModulo;
        } catch (Exception e) {
            return "redirect:/administrador/contenidos/modulo/" + idModulo + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/actualizar/{idContenido}")
    public String actualizarContenido(
            @PathVariable Integer idContenido,
            @RequestParam Integer idModulo,
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam(required = false) Integer orden) {

        try {
            contenidoService.actualizarContenido(idContenido, titulo, descripcion, orden);
            return "redirect:/administrador/contenidos/modulo/" + idModulo;
        } catch (Exception e) {
            return "redirect:/administrador/contenidos/modulo/" + idModulo + "?error=" + e.getMessage();
        }
    }

    // Eliminar contenido
    @DeleteMapping("/eliminar/{idContenido}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> eliminarContenido(@PathVariable Integer idContenido) {

        Map<String, Object> response = new HashMap<>();

        try {
            contenidoService.eliminarContenido(idContenido);

            response.put("success", true);
            response.put("message", "Contenido eliminado exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}