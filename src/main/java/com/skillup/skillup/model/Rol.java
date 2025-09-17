package com.skillup.skillup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @Column(name = "idRol")
    private Integer idRol;

    @Column(name = "descripcion")
    private String descripcion;

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Rol() {
    }


    public Rol(Integer idRol, String descripcion) {
        this.idRol = idRol;
        this.descripcion = descripcion;
    }
}