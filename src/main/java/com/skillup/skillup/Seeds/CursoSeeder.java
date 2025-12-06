package com.skillup.skillup.Seeds;


import com.skillup.skillup.model.Curso;
import com.skillup.skillup.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CursoSeeder implements CommandLineRunner {

    private final CursoRepository cursoRepository;

    public CursoSeeder(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> nuevosCursos = Arrays.asList(
                "Inteligencia Emocional"

        );

        nuevosCursos.forEach(nombre -> {
            if (!cursoRepository.existsByNombre(nombre)) {
                Curso curso = new Curso();
                curso.setNombre(nombre);
                cursoRepository.save(curso);
                System.out.println("Curso agregado: " + nombre);
            } else {
                System.out.println("Curso ya existe: " + nombre);
            }
        });
    }

}