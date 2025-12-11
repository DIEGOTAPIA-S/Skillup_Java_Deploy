package com.skillup.skillup.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "modulos")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODULO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_CURSOS")
    private Curso curso;

    @Column(name = "NOMBRE_MODULO")
    private String nombre;

    @Column(name = "DESCRIPCION", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ORDEN")
    private Integer orden;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contenido> contenidos = new HashSet<>();

    public Modulo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Set<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
    }
}
