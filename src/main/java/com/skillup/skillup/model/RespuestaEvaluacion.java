package com.skillup.skillup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "respuestas_evaluacion")
public class RespuestaEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESPUESTA")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_EVALUACION")
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "ID_PREGUNTA")
    private PreguntaEvaluacion pregunta;

    @Column(name = "RESPUESTA_SELECCIONADA")
    private String respuestaSeleccionada; // 'A', 'B', 'C', 'D'

    @Column(name = "ES_CORRECTA")
    private Boolean esCorrecta;

    // Constructores, Getters y Setters
    public RespuestaEvaluacion() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public PreguntaEvaluacion getPregunta() {
        return pregunta;
    }

    public void setPregunta(PreguntaEvaluacion pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestaSeleccionada() {
        return respuestaSeleccionada;
    }

    public void setRespuestaSeleccionada(String respuestaSeleccionada) {
        this.respuestaSeleccionada = respuestaSeleccionada;
    }

    public Boolean getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(Boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }
}