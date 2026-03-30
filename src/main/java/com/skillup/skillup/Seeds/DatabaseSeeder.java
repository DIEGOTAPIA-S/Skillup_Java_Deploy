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
import com.skillup.skillup.repository.ProgresoModuloRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final RegistrarseRepository registrarseRepository;
    private final CursoRepository cursoRepository;
    private final ModuloRepository moduloRepository;
    private final ContenidoRepository contenidoRepository;
    private final ProgresoModuloRepository progresoModuloRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public DatabaseSeeder(RolRepository rolRepository,
            RegistrarseRepository registrarseRepository,
            CursoRepository cursoRepository,
            ModuloRepository moduloRepository,
            ContenidoRepository contenidoRepository,
            ProgresoModuloRepository progresoModuloRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.registrarseRepository = registrarseRepository;
        this.cursoRepository = cursoRepository;
        this.moduloRepository = moduloRepository;
        this.contenidoRepository = contenidoRepository;
        this.progresoModuloRepository = progresoModuloRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
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
            Curso curso = cursoRepository.findByNombre(nombre).orElse(null);
            
            if (curso == null) {
                curso = new Curso();
                curso.setNombre(nombre);
                cursoRepository.save(curso);
            }

            // Actualizamos la descripción a una más profesional
            String desc = "Fortalece tus competencias en " + nombre + " con este programa experto.";
            String img = "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=800";
            
            if (nombre.equals("Inteligencia Emocional")) {
                desc = "Domina tus emociones y mejora tus relaciones interpersonales con técnicas de Daniel Goleman.";
                img = "https://images.unsplash.com/photo-1506126613408-eca07ce68773?w=800";
            } else if (nombre.equals("Liderazgo Efectivo")) {
                desc = "Aprende a guiar equipos de alto rendimiento, delegar con confianza y ser un líder inspirador.";
                img = "https://images.unsplash.com/photo-1519389950473-47ba0277781c?w=800";
            } else if (nombre.equals("Comunicación Asertiva")) {
                desc = "Expresa tus ideas con seguridad y respeto. Aprende a decir no sin culpa y dar feedback constructivo.";
                img = "https://images.unsplash.com/photo-1521791136064-7986c2923216?w=800";
            }
            
            curso.setDescripcion(desc);
            curso.setImagenUrl(img);
            cursoRepository.save(curso);

            // Optimizacion para produccion: Solo recargamos si el curso NO tiene modulos
            // Esto evita que Render de timeout por operaciones pesadas de DB al arrancar
            if (nombre.equals("Inteligencia Emocional") || 
                nombre.equals("Liderazgo Efectivo") || 
                nombre.equals("Comunicación Asertiva")) {
                
                long conteoModulos = moduloRepository.countByCurso_Id(curso.getId());
                
                if (conteoModulos == 0) {
                    System.out.println("Sembrando modulos para: " + nombre);
                    if (nombre.equals("Inteligencia Emocional")) seedInteligenciaEmocional(curso);
                    else if (nombre.equals("Liderazgo Efectivo")) seedLiderazgoEfectivo(curso);
                    else if (nombre.equals("Comunicación Asertiva")) seedComunicacionAsertiva(curso);
                }
            } else {
                // Para los demás cursos, si no tienen módulos, ponemos la estructura genérica
                if (moduloRepository.findByCurso_IdOrderByOrdenAsc(curso.getId()).isEmpty()) {
                    cargarEstructuraGenerica(curso, nombre);
                }
            }
        });
    }

    private void seedInteligenciaEmocional(Curso curso) {
        Modulo m1 = crearModulo(curso, "1. Introducción a la IE", "Conoce qué es la IE y por qué es clave en el siglo XXI.", 1);
        crearContenido(m1, "Bienvenida e Introducción", "https://www.youtube.com/watch?v=SePZ660-MIs", 1);
        crearContenido(m1, "Los 5 Pilares de Daniel Goleman", "https://es.wikipedia.org/wiki/Inteligencia_emocional#Los_cinco_elementos_de_la_inteligencia_emocional", 2);

        Modulo m2 = crearModulo(curso, "2. Autoconocimiento y Autorregulación", "Aprende a identificar tus disparadores emocionales.", 2);
        crearContenido(m2, "Técnicas de Respiración y Calma", "https://www.youtube.com/watch?v=3n-vT_6G5Ac", 1);
        crearContenido(m2, "Diario de Emociones", "https://psicologiaymente.com/blog/diario-de-emociones", 2);

        Modulo m3 = crearModulo(curso, "3. Empatía y Habilidades Sociales", "Cómo conectar con los demás de forma sana.", 3);
        crearContenido(m3, "Escucha Activa y Empatía", "https://www.youtube.com/watch?v=fS8mYiv1v28", 1);
    }

    private void seedLiderazgoEfectivo(Curso curso) {
        Modulo m1 = crearModulo(curso, "1. El Líder Moderno", "Diferencia entre jefe y líder. Estilos de liderazgo.", 1);
        crearContenido(m1, "Simon Sinek: Empieza con el porqué", "https://www.youtube.com/watch?v=ReRcHdeUG9Y", 1);
        
        Modulo m2 = crearModulo(curso, "2. Liderazgo Situacional", "Cómo adaptar tu estilo según el equipo.", 2);
        crearContenido(m2, "Delegación Efectiva", "https://www.youtube.com/watch?v=Xp0N1f8J55c", 1);

        Modulo m3 = crearModulo(curso, "3. Motivación de Equipos", "Mantener el compromiso y la energía alta.", 3);
        crearContenido(m3, "La ciencia de la motivación", "https://www.youtube.com/watch?v=u6XAPnuFjJc", 1);
    }

    private void seedComunicacionAsertiva(Curso curso) {
        Modulo m1 = crearModulo(curso, "1. Bases de la Comunicación", "Escucha activa y lenguaje no verbal.", 1);
        crearContenido(m1, "El poder del lenguaje corporal - TED", "https://www.youtube.com/watch?v=P2fV6j1Z_I8", 1);

        Modulo m2 = crearModulo(curso, "2. Comunicación Asertiva vs Agresiva", "Cómo decir 'No' y feedback constructivo.", 2);
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
        if (!registrarseRepository.existsByIdentificacion(id) && !registrarseRepository.existsByCorreo(correo)) {
            Usuario u = new Usuario();
            u.setIdentificacion(id);
            u.setNombre(nombre);
            u.setApellido1(apellido);
            u.setApellido2("");
            u.setCorreo(correo);
            u.setContrasena(passwordEncoder.encode(pass));
            u.setIdRol(rol);
            u.setMfaSecret(null); // Para que el usuario deba configurarlo al primer login
            registrarseRepository.save(u);
            System.out.println("Usuario de prueba creado: " + nombre + " (" + id + ")");
        }
    }
}
