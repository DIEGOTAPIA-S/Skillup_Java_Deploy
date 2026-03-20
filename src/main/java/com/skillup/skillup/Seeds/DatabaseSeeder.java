package com.skillup.skillup.Seeds;

import java.util.List;
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
                curso.setDescripcion("Fortalece tus competencias en " + nombre + " con este programa diseñado por expertos.");

                // Imágenes temáticas
                if (nombre.contains("Inteligencia"))
                    curso.setImagenUrl("https://images.unsplash.com/photo-1506126613408-eca07ce68773?w=800&q=80");
                else if (nombre.contains("Liderazgo"))
                    curso.setImagenUrl("https://images.unsplash.com/photo-1519389950473-47ba0277781c?w=800&q=80");
                else if (nombre.contains("Comunicación"))
                    curso.setImagenUrl("https://images.unsplash.com/photo-1521791136064-7986c2923216?w=800&q=80");
                else
                    curso.setImagenUrl("https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=800&q=80");

                cursoRepository.save(curso);

                // CONTENIDO PREMIUM PARA DEMO (Cursos específicos)
                if (nombre.equals("Inteligencia Emocional")) {
                    seedInteligenciaEmocional(curso);
                } else if (nombre.equals("Liderazgo Efectivo")) {
                    seedLiderazgoEfectivo(curso);
                } else if (nombre.equals("Comunicación Asertiva")) {
                    seedComunicacionAsertiva(curso);
                } else {
                    cargarEstructuraGenerica(curso, nombre);
                }
            }
        });
    }

    private void seedInteligenciaEmocional(Curso curso) {
        Modulo m1 = crearModulo(curso, "Fundamentos de la Inteligencia Emocional", "Conoce qué es la IE y por qué es clave en el siglo XXI.", 1);
        crearContenido(m1, "Bienvenida e Introducción", "https://www.youtube.com/watch?v=1-S90HOnu9M", 1);
        crearContenido(m1, "Los 5 Pilares de Daniel Goleman", "Guía detallada sobre Autoconocimiento, Autocontrol, Automotivación, Empatía y Habilidades Sociales.", 2);

        Modulo m2 = crearModulo(curso, "Autoconocimiento y Autorregulación", "Aprende a identificar tus disparadores emocionales.", 2);
        crearContenido(m2, "Técnicas de Respiración y Calma", "https://www.youtube.com/watch?v=8Vw_v4_7RQA", 1);
    }

    private void seedLiderazgoEfectivo(Curso curso) {
        Modulo m1 = crearModulo(curso, "El Líder Moderno", "Diferencia entre jefe y líder. Estilos de liderazgo.", 1);
        crearContenido(m1, "Simon Sinek: Los líderes comen al final", "https://www.youtube.com/watch?v=hZ_vR_0Yqas", 1);
        
        Modulo m2 = crearModulo(curso, "Liderazgo Situacional", "Cómo adaptar tu estilo según el equipo.", 2);
        crearContenido(m2, "Delegación Efectiva", "https://www.youtube.com/watch?v=Xp0N1f8J55c", 1);
    }

    private void seedComunicacionAsertiva(Curso curso) {
        Modulo m1 = crearModulo(curso, "Bases de la Comunicación", "Escucha activa y lenguaje no verbal.", 1);
        crearContenido(m1, "El poder del lenguaje corporal", "https://www.youtube.com/watch?v=Ks-_Mh1QhMc", 1);

        Modulo m2 = crearModulo(curso, "Comunicación Asertiva vs Agresiva", "Cómo decir 'No' y feedback constructivo.", 2);
        crearContenido(m2, "El método del sándwich para feedback", "https://www.youtube.com/watch?v=FjIuCqDIs6o", 1);
    }

    private Modulo crearModulo(Curso curso, String nombre, String desc, int orden) {
        Modulo m = new Modulo();
        m.setCurso(curso);
        m.setNombre(nombre);
        m.setDescripcion(desc);
        m.setOrden(orden);
        return moduloRepository.save(m);
    }

    private void crearContenido(Modulo m, String titulo, String desc, int orden) {
        Contenido c = new Contenido();
        c.setModulo(m);
        c.setTitulo(titulo);
        c.setDescripcion(desc);
        c.setOrden(orden);
        contenidoRepository.save(c);
    }

    private void cargarEstructuraGenerica(Curso curso, String nombreCurso) {
        // Módulo 1: Fundamentos
        Modulo m1 = new Modulo();
        m1.setCurso(curso);
        m1.setNombre("Introducción a " + nombreCurso);
        m1.setDescripcion("Conceptos fundamentales y su importancia en el entorno profesional.");
        m1.setOrden(1);
        moduloRepository.save(m1);

        Contenido c1 = new Contenido();
        c1.setModulo(m1);
        c1.setTitulo("¿Qué es " + nombreCurso + "?");
        c1.setDescripcion("Definición y pilares básicos de esta competencia.");
        c1.setOrden(1);
        contenidoRepository.save(c1);

        // Módulo 2: Aplicación Práctica
        Modulo m2 = new Modulo();
        m2.setCurso(curso);
        m2.setNombre("Estrategias y Técnicas");
        m2.setDescripcion("Cómo aplicar " + nombreCurso + " en el día a día.");
        m2.setOrden(2);
        moduloRepository.save(m2);

        Contenido c2 = new Contenido();
        c2.setModulo(m2);
        c2.setTitulo("Ejercicios de Aplicación");
        c2.setDescripcion("Guía paso a paso para fortalecer esta habilidad.");
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
