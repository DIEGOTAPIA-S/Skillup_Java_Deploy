package com.skillup.skillup.Dto;


import com.skillup.skillup.model.Inscripcion;

public class CertificadoDTO {

    private Inscripcion inscripcion;
    private boolean modulosTerminados;
    private String estadoLabel;

    public CertificadoDTO(Inscripcion inscripcion, boolean modulosTerminados, String estadoLabel) {
        this.inscripcion = inscripcion;
        this.modulosTerminados = modulosTerminados;
        this.estadoLabel = estadoLabel;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public boolean isModulosTerminados() {
        return modulosTerminados;
    }

    public String getEstadoLabel() {
        return estadoLabel;
    }
}