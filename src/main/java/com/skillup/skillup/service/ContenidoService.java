package com.skillup.skillup.service;

import com.skillup.skillup.model.Contenido;
import com.skillup.skillup.model.Modulo;
import com.skillup.skillup.repository.ContenidoRepository;
import com.skillup.skillup.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    // Obtener contenidos de un módulo
    public List<Contenido> obtenerContenidosPorModulo(Integer idModulo) {
        return contenidoRepository.findByModulo_IdOrderByOrdenAsc(idModulo);
    }

    // Crear nuevo contenido
    @Transactional
    public Contenido crearContenido(Integer idModulo, String titulo, Integer orden, String contenidoTexto, String contenidoVideo, String contenidoPdf, String contenidoLink) {
        Modulo modulo = moduloRepository.findById(idModulo)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado: " + idModulo));

        Contenido contenido = new Contenido();
        contenido.setModulo(modulo);
        contenido.setTitulo(titulo.trim());
        contenido.setOrden(orden != null ? orden : 1);
        contenido.setContenidoTexto(blankToNull(contenidoTexto));
        contenido.setContenidoVideo(blankToNull(contenidoVideo));
        contenido.setContenidoPdf(blankToNull(contenidoPdf));
        contenido.setContenidoLink(blankToNull(contenidoLink));

        return contenidoRepository.save(contenido);
    }

    // Actualizar contenido
    @Transactional
    public Contenido actualizarContenido(Integer idContenido, String titulo, Integer orden, String contenidoTexto, String contenidoVideo, String contenidoPdf, String contenidoLink) {
        Contenido contenido = contenidoRepository.findById(idContenido)
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado: " + idContenido));

        contenido.setTitulo(titulo.trim());
        if(orden != null) contenido.setOrden(orden);
        contenido.setContenidoTexto(blankToNull(contenidoTexto));
        contenido.setContenidoVideo(blankToNull(contenidoVideo));
        contenido.setContenidoPdf(blankToNull(contenidoPdf));
        contenido.setContenidoLink(blankToNull(contenidoLink));

        return contenidoRepository.save(contenido);
    }

    // Eliminar contenido
    @Transactional
    public void eliminarContenido(Integer idContenido) {
        contenidoRepository.deleteById(idContenido);
    }

    // Obtener un contenido por ID
    public Contenido obtenerContenidoPorId(Integer idContenido) {
        return contenidoRepository.findById(idContenido)
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado: " + idContenido));
    }

    private String blankToNull(String s){
        return(s == null || s.isBlank()) ? null : s.trim();
    }
}