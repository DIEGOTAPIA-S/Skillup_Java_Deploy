package com.skillup.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "roles_sistema")
public class Usuario implements Serializable {

    @Id
    @Column(name = "IDENTIFICACION")
    @NotNull(message = "La identificación es obligatoria.")
    @Pattern(regexp = "\\d{6,15}", message = "Solo se permiten números de 6 a 15 dígitos.")
    private String identificacion;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @Column(name = "APELLIDO1", nullable = false, length = 50)
    @NotBlank(message = "El primer apellido es obligatorio.")
    private String apellido1;

    @Column(name = "APELLIDO2", length = 50)
    private String apellido2;

    @Column(name = "CORREO", nullable = false, unique = true)
    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Debe ser un correo válido.")
    private String correo;

    @Column(name = "CONTRASEÑA", nullable = false)
    @NotBlank(message = "Debe ingresar una contraseña.")
    private String contrasena; // Usaremos contrasena en Java pero mapeado a CONTRASEÑA

    @Column(name = "idRol", nullable = false)
    @NotNull(message = "El rol es obligatorio.")
    private Integer idRol;

    @Column(name = "mfa_secret")
    private String mfaSecret;

    public Usuario() {}

    // Getters y Setters
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }

    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }

    public String getMfaSecret() { return mfaSecret; }
    public void setMfaSecret(String mfaSecret) { this.mfaSecret = mfaSecret; }
}
