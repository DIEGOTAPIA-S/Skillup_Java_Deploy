package com.skillup.skillup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/plantillas")
public class PlantillaController {

    @GetMapping("/{nombre}")
    public ResponseEntity<String> cargarPlantilla(@PathVariable String nombre) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream("templates/correos/" + nombre + ".html");

        if (inputStream == null) {
            return ResponseEntity.status(404).body("PLANTILLA NO ENCONTRADA");
        }

        String contenido = new String(inputStream.readAllBytes());
        return ResponseEntity.ok(contenido);
    }
}
