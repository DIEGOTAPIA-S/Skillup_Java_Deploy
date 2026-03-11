package com.skillup.skillup.Seeds;

import com.skillup.skillup.model.Rol;
import com.skillup.skillup.model.Usuario;
import com.skillup.skillup.model.Curso;
import com.skillup.skillup.model.Modulo;
import com.skillup.skillup.model.Contenido;
import com.skillup.skillup.repository.RolRepository;
import com.skillup.skillup.repository.RegistrarseRepository;
import com.skillup.skillup.repository.CursoRepository;
import com.skillup.skillup.repository.ModuloRepository;
import com.skillup.skillup.repository.ContenidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final RegistrarseRepository registrarseRepository;
    private final CursoRepository cursoRepository;
    private final ModuloRepository moduloRepository;
    private final ContenidoRepository contenidoRepository;

    public DatabaseSeeder(RolRepository rolRepository,
            RegistrarseRepository registrarseRepository,
            CursoRepository cursoRepository,
            ModuloRepository moduloRepository,
            ContenidoRepository contenidoRepository) {
        this.rolRepository = rolRepository;
        this.registrarseRepository = registrarseRepository;
        this.cursoRepository = cursoRepository;
        this.moduloRepository = moduloRepository;
        this.contenidoRepository = contenidoRepository;
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

        // 10 Estudiantes con nombres realistas
        crearUsuarioSiNoExiste("300300", "Juan", "Castañeda", "Estudiante123*", 2, "juan.c@skillup.com");
        crearUsuarioSiNoExiste("300301", "María", "Gómez", "Estudiante123*", 2, "maria.g@skillup.com");
        crearUsuarioSiNoExiste("300302", "Andrés", "Rodríguez", "Estudiante123*", 2, "andres.r@skillup.com");
        crearUsuarioSiNoExiste("300303", "Paula", "Martínez", "Estudiante123*", 2, "paula.m@skillup.com");
        crearUsuarioSiNoExiste("300304", "Luis", "Fernández", "Estudiante123*", 2, "luis.f@skillup.com");
        crearUsuarioSiNoExiste("300305", "Diana", "Pérez", "Estudiante123*", 2, "diana.p@skillup.com");
        crearUsuarioSiNoExiste("300306", "Camilo", "Torres", "Estudiante123*", 2, "camilo.t@skillup.com");
        crearUsuarioSiNoExiste("300307", "Isabella", "Ruiz", "Estudiante123*", 2, "isabella.r@skillup.com");
        crearUsuarioSiNoExiste("300308", "Jorge", "Suárez", "Estudiante123*", 2, "jorge.s@skillup.com");
        crearUsuarioSiNoExiste("300309", "Lucía", "Quintero", "Estudiante123*", 2, "lucia.q@skillup.com");

        // 3. CARGAR CURSOS DE HABILIDADES BLANDAS
        List<String> habilidadesBlandas = Arrays.asList(
                "Inteligencia Emocional",
                "Liderazgo Efectivo",
                "Comunicación Asertiva",
                "Trabajo en Equipo",
                "Negociación y Persuasión",
                "Pensamiento Crítico",
                "Resolución de Conflictos",
                "Gestión del Tiempo",
                "Adaptabilidad y Flexibilidad",
                "Empatía en el Entorno Laboral");

        habilidadesBlandas.forEach(nombre -> {
            if (!cursoRepository.existsByNombre(nombre)) {
                Curso curso = new Curso();
                curso.setNombre(nombre);
                curso.setDescripcion("Este curso está diseñado para fortalecer tus competencias en " + nombre
                        + ", una de las habilidades blandas más valoradas en el mercado laboral actual.");

                // Imágenes temáticas según el curso
                if (nombre.contains("Inteligencia"))
                    curso.setImagenUrl("https://images.unsplash.com/photo-1506126613408-eca07ce68773?w=800&q=80");
                else if (nombre.contains("Liderazgo"))
                    curso.setImagenUrl("https://images.unsplash.com/photo-1519389950473-47ba0277781c?w=800&q=80");
                else if (nombre.contains("Comunicación"))
                    curso.setImagenUrl("https://images.unsplash.com/photo-1521791136064-7986c2923216?w=800&q=80");
                else if (nombre.contains("Equipo"))
                    curso.setImagenUrl("https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=800&q=80");
                else if (nombre.contains("Negociación"))
                    curso.setImagenUrl("https://images.unsplash.com/photo-1573497019940-1c28c88b4f3e?w=800&q=80");
                else
                    curso.setImagenUrl("https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=800&q=80");

                cursoRepository.save(curso);

                // Estructura para el curso principal
                if (nombre.equals("Inteligencia Emocional")) {
                    cargarEstructuraCurso(curso);
                }
            }
        });
    }

    private void cargarEstructuraCurso(Curso curso) {
        // Módulo 1
        Modulo m1 = new Modulo();
        m1.setCurso(curso);
        m1.setNombre("Introducción a la Inteligencia Emocional");
        m1.setDescripcion("Conceptos básicos y pilares de la IE.");
        m1.setOrden(1);
        moduloRepository.save(m1);

        Contenido c1 = new Contenido();
        c1.setModulo(m1);
        c1.setTitulo("¿Qué es la Inteligencia Emocional?");
        c1.setDescripcion("La inteligencia emocional es la capacidad de reconocer sentimientos propios y ajenos.");
        c1.setOrden(1);
        contenidoRepository.save(c1);

        // Módulo 2
        Modulo m2 = new Modulo();
        m2.setCurso(curso);
        m2.setNombre("Autoconocimiento y Autorregulación");
        m2.setDescripcion("Aprende a identificar tus emociones en tiempo real.");
        m2.setOrden(2);
        moduloRepository.save(m2);

        Contenido c2 = new Contenido();
        c2.setModulo(m2);
        c2.setTitulo("Técnicas de Respiración");
        c2.setDescripcion("Ejercicios prácticos para mantener la calma bajo presión.");
        c2.setOrden(1);
        contenidoRepository.save(c2);
    }

    private void crearUsuarioSiNoExiste(String id, String nombre, String apellido, String pass, Integer rol,
            String correo) {
        if (!registrarseRepository.existsByIdentificacion(id)) {
            Usuario u = new Usuario();
            u.setIdentificacion(id);
            u.setNombre(nombre);
            u.setApellido1(apellido);
            u.setApellido2("");
            u.setCorreo(correo);
            u.setContrasena(pass);
            u.setIdRol(rol);
            registrarseRepository.save(u);
            System.out.println("Usuario de prueba creado: " + nombre + " (" + id + ")");
        }
    }
}
