package com.skillup.skillup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contenidos")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTENIDO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_MODULO")
    private Modulo modulo;

    @Column(name = "TITULO", length = 200)
    private String titulo;

    @Column(name = "DESCRIPCION", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "contenido_texto", columnDefinition = "TEXT")
    private String contenidoTexto;

    @Column(name = "contenido_pdf", length = 500)
    private String contenidoPdf;

    @Column(name = "contenido_link", length = 500)
    private String contenidoLink;

    @Column(name = "contenido_video", length = 500)
    private String contenidoVideo;

    @Column(name = "ORDEN")
    private Integer orden = 1;

    // Constructores
    public Contenido() {}

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenidoTexto() { return contenidoTexto; }
    public void setContenidoTexto(String contenidoTexto) { this.contenidoTexto = contenidoTexto; }

    public String getContenidoPdf() { return contenidoPdf; }
    public void setContenidoPdf(String contenidoPdf) { this.contenidoPdf = contenidoPdf; }

    public String getContenidoLink() { return contenidoLink; }
    public void setContenidoLink(String contenidoLink) { this.contenidoLink = contenidoLink; }

    public String getContenidoVideo() { return contenidoVideo; }
    public void setContenidoVideo(String contenidoVideo) { this.contenidoVideo = contenidoVideo; }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}