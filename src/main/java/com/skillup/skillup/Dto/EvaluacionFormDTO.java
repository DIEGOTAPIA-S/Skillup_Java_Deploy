package com.skillup.skillup.Dto;

import java.util.List;

public class EvaluacionFormDTO {

    private Integer idCurso;
    private String comentarios;

    private List<PreguntaDTO> preguntas;

    // Getters y Setters
    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public List<PreguntaDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaDTO> preguntas) {
        this.preguntas = preguntas;
    }

    // DTO interno para preguntas
    public static class PreguntaDTO {
        private String pregunta;
        private String opcionA;
        private String opcionB;
        private String opcionC;
        private String opcionD;
        private String respuestaCorrecta;

        public String getPregunta() { return pregunta; }
        public void setPregunta(String pregunta) { this.pregunta = pregunta; }
        public String getOpcionA() { return opcionA; }
        public void setOpcionA(String opcionA) { this.opcionA = opcionA; }
        public String getOpcionB() { return opcionB; }
        public void setOpcionB(String opcionB) { this.opcionB = opcionB; }
        public String getOpcionC() { return opcionC; }
        public void setOpcionC(String opcionC) { this.opcionC = opcionC; }
        public String getOpcionD() { return opcionD; }
        public void setOpcionD(String opcionD) { this.opcionD = opcionD; }
        public String getRespuestaCorrecta() { return respuestaCorrecta; }
        public void setRespuestaCorrecta(String respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }
    }
}