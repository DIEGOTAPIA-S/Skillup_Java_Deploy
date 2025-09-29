package com.skillup.skillup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSOS")
    private Integer id;

    @Column(name = "NOMBRE_CURSO", nullable = false, length = 255)
    private String nombreCurso;

    @Column(name = "NIVEL_DIFICULTAD", length = 100)
    private String nivelDificultad;

    @Column(name = "DURACION", length = 50)
    private String duracion;

    @Column(name = "ID_EVALUACIONES")
    private Integer idEvaluaciones;

    // Relación con la tabla Usuarios (IDENTIFICACION)
    @ManyToOne
    @JoinColumn(name = "IDENTIFICACION", referencedColumnName = "identificacion")
    private Usuarios usuario;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(String nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Integer getIdEvaluaciones() {
        return idEvaluaciones;
    }

    public void setIdEvaluaciones(Integer idEvaluaciones) {
        this.idEvaluaciones = idEvaluaciones;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}