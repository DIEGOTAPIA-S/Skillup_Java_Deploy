package com.skillup.skillup.service;

import com.skillup.skillup.Dto.CursoDTO;
import com.skillup.skillup.mapper.CursoMapper;
import com.skillup.skillup.model.Contenido;
import com.skillup.skillup.model.Curso;
import com.skillup.skillup.model.Modulo;
import com.skillup.skillup.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CursoMapper cursoMapper;

    @Transactional
    public Curso guardarCursoCompleto(CursoDTO cursoDTO) {
        if (cursoRepository.existsByNombre(cursoDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un curso con el nombre: " + cursoDTO.getNombre());
        }

        Curso curso = cursoMapper.toEntity(cursoDTO);
        return cursoRepository.save(curso);
    }

    public boolean existeCursoPorNombre(String nombre) {
        return cursoRepository.existsByNombre(nombre);
    }

    public CursoDTO obtenerCursoPorId(Integer id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + id));
        return cursoMapper.toDTO(curso);
    }

    @Transactional(readOnly = true)
    public Curso obtenerCursoConModulos(Integer id) {
        return cursoRepository.findByIdWithModulosAndContenidos(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + id));
    }

    public Curso obtenerCursoEntity(Integer id) {
        return obtenerCursoConModulos(id);
    }
}