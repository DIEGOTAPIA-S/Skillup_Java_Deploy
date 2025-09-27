package com.skillup.skillup.service;

import com.skillup.skillup.model.CurriculumSeccion;
import com.skillup.skillup.model.Curso;
import com.skillup.skillup.model.Rol;
import com.skillup.skillup.model.Usuarios;
import com.skillup.skillup.repository.CurriculumSeccionRepository;
import com.skillup.skillup.repository.CursoRepository;
import com.skillup.skillup.repository.RolRepository;
import com.skillup.skillup.repository.UsuariosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MiCuentaService {

    private final UsuariosRepository usuariosRepository;
    private final RolRepository rolRepository;
    private final CursoRepository cursoRepository;
    private final CurriculumSeccionRepository curriculumSeccionRepository;

    public MiCuentaService(UsuariosRepository usuariosRepository,
                           RolRepository rolRepository,
                           CursoRepository cursoRepository,
                           CurriculumSeccionRepository curriculumSeccionRepository) {
        this.usuariosRepository = usuariosRepository;
        this.rolRepository = rolRepository;
        this.cursoRepository = cursoRepository;
        this.curriculumSeccionRepository = curriculumSeccionRepository;
    }

    public Optional<Usuarios> obtenerUsuario(Integer identificacion) {
        return usuariosRepository.findById(identificacion);
    }

    public Optional<Rol> obtenerRol(Integer idRol) {
        return rolRepository.findById(idRol);
    }

    public List<Curso> obtenerCursosPorEstudiante(Integer identificacion) {
        return cursoRepository.findByIdentificacion(identificacion);
    }

    public Map<String,String> obtenerContenidoSecciones(Integer identificacion, String... titulos) {
        Map<String,String> mapa = new HashMap<>();
        for (String t : titulos) {
            curriculumSeccionRepository
                    .findByUsuario_IdentificacionAndSeccion(identificacion, t)
                    .ifPresent(sec -> mapa.put(t, sec.getContenido()));
        }
        return mapa;
    }

    public void guardarSeccion(Integer identificacion, String seccion, String contenido) {
        Usuarios usuario = usuariosRepository.findById(identificacion)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado: " + identificacion));

        curriculumSeccionRepository.findByUsuario_IdentificacionAndSeccion(identificacion, seccion)
                .ifPresentOrElse(
                        existente -> {
                            existente.setContenido(contenido);
                            curriculumSeccionRepository.save(existente);
                        },
                        () -> {
                            CurriculumSeccion nueva = new CurriculumSeccion(usuario, seccion, contenido);
                            curriculumSeccionRepository.save(nueva);
                        }
                );
    }

    public void eliminarSeccion(Integer identificacion, String seccion) {
        curriculumSeccionRepository.findByUsuario_IdentificacionAndSeccion(identificacion, seccion)
                .ifPresent(curriculumSeccionRepository::delete);
    }

    public void actualizarPerfil(Integer identificacion, String nombre, String apellido1, String apellido2, String correo) {
        Usuarios u = usuariosRepository.findById(identificacion)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado: " + identificacion));

        u.setNombre(nombre);
        u.setApellido1(apellido1);
        u.setApellido2(apellido2);
        u.setCorreo(correo);

        usuariosRepository.save(u);
    }
}
