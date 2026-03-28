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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            model.addAttribute("error", "Error: " + e.getMessage());
            return "redirect:/administrador/cursos/listar";
        }
    }

    @PostMapping("/crear")
    public String crearContenido(
            @RequestParam Integer idModulo,
            @RequestParam String titulo,
            @RequestParam(required = false) Integer orden,
            @RequestParam(required = false) String contenidoTexto,
            @RequestParam(required = false) String contenidoVideo,
            @RequestParam(required = false) String contenidoPdf,
            @RequestParam(required = false) String contenidoLink
            ) {

        try {
            contenidoService.crearContenido(idModulo, titulo, orden, contenidoTexto, contenidoVideo, contenidoPdf, contenidoLink);
        } catch (Exception e) {
            return "redirect:/administrador/contenidos/modulo/" + idModulo + "?error=" + e.getMessage();
        }
        return "redirect:/administrador/contenidos/modulo/" + idModulo;

    }

    @PostMapping("/actualizar/{idContenido}")
    public String actualizarContenido(
            @RequestParam Integer idContenido,
            @RequestParam Integer idModulo,
            @RequestParam String titulo,
            @RequestParam(required = false) Integer orden,
            @RequestParam(required = false) String contenidoTexto,
            @RequestParam(required = false) String contenidoVideo,
            @RequestParam(required = false) String contenidoPdf,
            @RequestParam(required = false) String contenidoLink){

        try {
            contenidoService.actualizarContenido(idContenido, titulo,  orden, contenidoTexto, contenidoVideo, contenidoPdf, contenidoLink);
        } catch (Exception e) {
            return "redirect:/administrador/contenidos/modulo/" + idModulo + "?error=" + e.getMessage();
        }
        return "redirect:/administrador/contenidos/modulo/" + idModulo;
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

    @PostMapping("/subirPDF")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> subirPDF(@RequestParam("file")MultipartFile file){
        Map<String, Object> response = new HashMap<>();
        try{
            if(file.isEmpty()){
                response.put("success", false);
                response.put("message", "El archivo está vacío");
                return ResponseEntity.badRequest().body(response);
            }
            if(!"apliccation/pdf".equals(file.getContentType())){
                response.put("success", false);
                response.put("message", "Tipo de archivo invalido");
                return ResponseEntity.badRequest().body(response);
            }
            if(file.getSize() > 10 * 1024 * 1024){
                response.put("success", false);
                response.put("message", "Tamanho exceido");
                return ResponseEntity.badRequest().body(response);
            }

            //NOMBRE UNICO PARA EVITAR COLISIONES
            String nombreSeguro = System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]","_");

            Path destino = Paths.get("src/main/reosurces/static/uploads/pdfs/" + nombreSeguro);
            Files.createDirectories(destino.getParent());
            Files.write(destino, file.getBytes());

            response.put("success", true);
            response.put("url", "/uploads/pdfs/" + nombreSeguro);
            return ResponseEntity.ok(response);
        }catch (IOException e){
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}