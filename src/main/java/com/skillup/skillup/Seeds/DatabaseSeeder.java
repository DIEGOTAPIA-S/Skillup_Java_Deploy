package com.skillup.skillup.Seeds;

import com.skillup.skillup.model.Rol;
import com.skillup.skillup.model.Registrarse;
import com.skillup.skillup.model.Curso;
import com.skillup.skillup.repository.RolRepository;
import com.skillup.skillup.repository.RegistrarseRepository;
import com.skillup.skillup.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final RegistrarseRepository registrarseRepository;
    private final CursoRepository cursoRepository;

    public DatabaseSeeder(RolRepository rolRepository,
            RegistrarseRepository registrarseRepository,
            CursoRepository cursoRepository) {
        this.rolRepository = rolRepository;
        this.registrarseRepository = registrarseRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. CARGAR ROLES
        if (rolRepository.count() == 0) {
            rolRepository.saveAll(Arrays.asList(
                    new Rol(1, "Administrador"),
                    new Rol(2, "Estudiante"),
                    new Rol(3, "Evaluador")));
            System.out.println("Roles iniciales cargados.");
        }

        // 2. CARGAR USUARIOS DE PRUEBA
        // Admin
        crearUsuarioSiNoExiste("1001", "Admin", "Skillup", "Admin123*", 1, "admin@skillup.com");
        // Estudiante
        crearUsuarioSiNoExiste("3003", "Juan", "Estudiante", "Estudiante123*", 2, "estudiante@skillup.com");
        // Evaluador
        crearUsuarioSiNoExiste("2002", "Marta", "Evaluadora", "Evaluador123*", 3, "evaluador@skillup.com");

        // 3. CARGAR CURSOS DE PRUEBA
        if (!cursoRepository.existsByNombre("Inteligencia Emocional")) {
            Curso curso = new Curso();
            curso.setNombre("Inteligencia Emocional");
            cursoRepository.save(curso);
            System.out.println("Curso de prueba cargado.");
        }
    }

    private void crearUsuarioSiNoExiste(String id, String nombre, String apellido, String pass, Integer rol,
            String correo) {
        if (!registrarseRepository.existsByIdentificacion(id)) {
            Registrarse u = new Registrarse();
            u.setIdentificacion(id);
            u.setNombre(nombre);
            u.setApellido1(apellido);
            u.setApellido2("");
            u.setCorreo(correo);
            u.setContraseña(pass);
            u.setIdRol(rol);
            registrarseRepository.save(u);
            System.out.println("Usuario de prueba creado: " + nombre + " (" + id + ")");
        }
    }
}
