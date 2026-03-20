package com.skillup.skillup.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "src/main/resources/static/uploads/pdfs";

    public String saveFile(MultipartFile file) throws IOException {
        // Crear directorio si no existe
        Path root = Paths.get(uploadDir);
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        // Nombre único para evitar colisiones
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        String filename = UUID.randomUUID().toString() + extension;
        Path target = root.resolve(filename);

        // Guardar archivo
        Files.copy(file.getInputStream(), target);

        // Retornar la URL relativa para el navegador
        return "/uploads/pdfs/" + filename;
    }
}
