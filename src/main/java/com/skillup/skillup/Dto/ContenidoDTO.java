package com.skillup.skillup.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContenidoDTO {

    @NotBlank(message = "El título del contenido es obligatorio")
    @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
    private String titulo;

    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
    private String descripcion;

    @NotNull(message = "El orden del contenido es obligatorio")
    @Min(value = 1, message = "El orden debe ser mayor a 0")
    private Integer orden;

    // Constructores
    public ContenidoDTO() {}

    public ContenidoDTO(String titulo, String descripcion, Integer orden) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.orden = orden;
    }

    // Getters y Setters
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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}