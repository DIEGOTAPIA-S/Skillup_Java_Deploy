package com.skillup.skillup.service;

import com.skillup.skillup.model.Curso;
import com.skillup.skillup.model.Inscripcion;
import com.skillup.skillup.model.Usuarios;
import com.skillup.skillup.repository.CursoRepository;
import com.skillup.skillup.repository.InscripcionRepository;
import com.skillup.skillup.repository.ModuloRepository;
import com.skillup.skillup.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class InscripcionService{

    @Autowired
    private InscripcionRepository inscripcionRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private UsuariosRepository usuariosRepo;

    @Autowired
    private ModuloRepository moduloRepo;

    public Integer inscribirEstudiante(Integer idCurso, String identificacionUsuario){
        if(inscripcionRepo.existsByCurso_IdAndUsuario_Identificacion(idCurso,identificacionUsuario)){
            throw new RuntimeException("Ya existe una inscripcion en este curso");
        }

        Curso curso = cursoRepo.findById(idCurso).orElseThrow();
        Usuarios usuario = usuariosRepo.findById(identificacionUsuario).orElseThrow();

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCurso(curso);
        inscripcion.setUsuario(usuario);
        inscripcionRepo.save(inscripcion);

        return moduloRepo.findByCurso_IdOrderByOrdenAsc(idCurso).get(0).getId();
    }
}