package com.skillup.skillup.Dto;

public class CursoReporteDto {
    private String nombreCurso;
    private Long totalInscripciones;

    // Constructor completo (importante para JPQL)
    public CursoReporteDto(String nombreCurso, Long totalInscripciones) {
        this.nombreCurso = nombreCurso;
        this.totalInscripciones = totalInscripciones;
    }

    // Getters y Setters
    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Long getTotalInscripciones() {
        return totalInscripciones;
    }

    public void setTotalInscripciones(Long totalInscripciones) {
        this.totalInscripciones = totalInscripciones;
    }
}