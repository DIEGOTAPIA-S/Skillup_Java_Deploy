package com.skillup.skillup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "preguntas_evaluacion")
public class PreguntaEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PREGUNTA")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_CURSOS")
    private Curso curso;

    @Column(name = "PREGUNTA", columnDefinition = "TEXT")
    private String pregunta;

    @Column(name = "OPCION_A")
    private String opcionA;

    @Column(name = "OPCION_B")
    private String opcionB;

    @Column(name = "OPCION_C")
    private String opcionC;

    @Column(name = "OPCION_D")
    private String opcionD;

    @Column(name = "RESPUESTA_CORRECTA")
    private String respuestaCorrecta; // 'A', 'B', 'C', 'D'

    @Column(name = "PUNTAJE")
    private Integer puntaje = 10;

    // Constructores, Getters y Setters
    public PreguntaEvaluacion() {}

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

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOpcionA() {
        return opcionA;
    }

    public void setOpcionA(String opcionA) {
        this.opcionA = opcionA;
    }

    public String getOpcionB() {
        return opcionB;
    }

    public void setOpcionB(String opcionB) {
        this.opcionB = opcionB;
    }

    public String getOpcionC() {
        return opcionC;
    }

    public void setOpcionC(String opcionC) {
        this.opcionC = opcionC;
    }

    public String getOpcionD() {
        return opcionD;
    }

    public void setOpcionD(String opcionD) {
        this.opcionD = opcionD;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }
}