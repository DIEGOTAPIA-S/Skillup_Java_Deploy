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
        crearUsuarioSiNoExiste("100100", "Admin", "Skillup", "Admin123*", 1, "admin@skillup.com");
        // Evaluador
        crearUsuarioSiNoExiste("200200", "Marta", "Evaluadora", "Evaluador123*", 3, "evaluador@skillup.com");

        // 10 Estudiantes
        crearUsuarioSiNoExiste("300300", "Juan", "Perez", "Estudiante123*", 2, "juan.perez@skillup.com");
        crearUsuarioSiNoExiste("300301", "Maria", "Garcia", "Estudiante123*", 2, "maria.garcia@skillup.com");
        crearUsuarioSiNoExiste("300302", "Carlos", "Rodriguez", "Estudiante123*", 2, "carlos.rod@skillup.com");
        crearUsuarioSiNoExiste("300303", "Ana", "Martinez", "Estudiante123*", 2, "ana.mtz@skillup.com");
        crearUsuarioSiNoExiste("300304", "Luis", "Lopez", "Estudiante123*", 2, "luis.lopez@skillup.com");
        crearUsuarioSiNoExiste("300305", "Elena", "Sanchez", "Estudiante123*", 2, "elena.sanchez@skillup.com");
        crearUsuarioSiNoExiste("300306", "Diego", "Torres", "Estudiante123*", 2, "diego.torres@skillup.com");
        crearUsuarioSiNoExiste("300307", "Sofia", "Ramirez", "Estudiante123*", 2, "sofia.ram@skillup.com");
        crearUsuarioSiNoExiste("300308", "Jorge", "Ruiz", "Estudiante123*", 2, "jorge.ruiz@skillup.com");
        crearUsuarioSiNoExiste("300309", "Lucia", "Castro", "Estudiante123*", 2, "lucia.castro@skillup.com");

        // 3. CARGAR CURSOS DE PRUEBA
        List<String> cursosNombres = Arrays.asList(
                "Inteligencia Emocional",
                "Programacion en Java",
                "Diseno de Interfaces (UI/UX)",
                "Gestion de Proyectos Agiles",
                "Comunicacion Asertiva",
                "Introduccion a Spring Boot");

        cursosNombres.forEach(nombre -> {
            if (!cursoRepository.existsByNombre(nombre)) {
                Curso curso = new Curso();
                curso.setNombre(nombre);
                cursoRepository.save(curso);
                System.out.println("Curso agregado: " + nombre);
            }
        });
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
