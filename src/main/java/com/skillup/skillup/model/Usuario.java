package com.skillup.skillup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles_sistema")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDENTIFICACION")
    private Long IDENTIFICACION;

    @Column(name = "NOMBRE")
    private String NOMBRE;

    @Column(name = "CORREO")
    private String CORREO;

    //getters and setters


    public Long getIDENTIFICACION() {
        return IDENTIFICACION;
    }

    public void setIDENTIFICACION(Long IDENTIFICACION) {
        this.IDENTIFICACION = IDENTIFICACION;
    }

    public String getNOMBRE() { return NOMBRE; }
    public void setNOMBRE(String NOMBRE) { this.NOMBRE = NOMBRE; }

    public String getCORREO() { return CORREO; }
    public void setCORREO(String CORREO) { this.CORREO = CORREO; }
}