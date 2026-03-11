package com.skillup.skillup.service;



import com.skillup.skillup.Dto.RegistroDTO;
import com.skillup.skillup.model.Usuario;
import com.skillup.skillup.repository.RegistrarseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class RegistrarseService {

    @Autowired
    private RegistrarseRepository registrarseRepository;

    @Autowired
    private MessageSource messageSource;

    private static final List<String> DOMINIOS_PERMITIDOS = Arrays.asList(
            "gmail.com", "hotmail.com", "outlook.com", "yahoo.com", "icloud.com", "skillup.com"
    );

    private static final Pattern PATTERN_IDENTIFICACION = Pattern.compile("^\\d{6,15}$");
    private static final Pattern PATTERN_NOMBRES = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$");
    private static final Pattern PATTERN_CONTRASEÑA = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");


    public List<String> validarRegistro(RegistroDTO dto) {
        List<String> errores = new ArrayList<>();
        Locale locale = Locale.getDefault();

        System.out.println("INICIANDO VALIDACIÓN PARA: " + dto.getCorreo());
        // Normalizar campos
        String identificacion = normalizar(dto.getIdentificacion());
        String nombre = normalizar(dto.getNombre());
        String apellido1 = normalizar(dto.getApellido1());
        String apellido2 = normalizar(dto.getApellido2());
        String correo = normalizar(dto.getCorreo());
        String contraseña = dto.getContraseña();
        String confirmarContraseña = dto.getConfirmarContraseña();

        // Actualizar DTO con valores normalizados
        dto.setIdentificacion(identificacion);
        dto.setNombre(nombre);
        dto.setApellido1(apellido1);
        dto.setApellido2(apellido2);
        dto.setCorreo(correo);

        // Validar campos vacíos
        if (identificacion.isEmpty() || nombre.isEmpty() || apellido1.isEmpty() ||
                correo.isEmpty() || contraseña == null || contraseña.isEmpty() ||
                confirmarContraseña == null || confirmarContraseña.isEmpty()) {
            errores.add(messageSource.getMessage("error.campos.obligatorios", null, locale));
            return errores; // Retornar inmediatamente si hay campos vacíos
        }

        // Validar identificación
        if (!PATTERN_IDENTIFICACION.matcher(identificacion).matches()) {
            errores.add(messageSource.getMessage("error.identificacion.formato", null, locale));
        }

        // Validar nombre
        if (!PATTERN_NOMBRES.matcher(nombre).matches()) {
            errores.add(messageSource.getMessage("error.nombre.formato", null, locale));
        }

        // Validar primer apellido
        if (!PATTERN_NOMBRES.matcher(apellido1).matches()) {
            errores.add(messageSource.getMessage("error.apellido1.formato", null, locale));
        }

        // Validar segundo apellido (solo si no está vacío)
        if (!apellido2.isEmpty() && !PATTERN_NOMBRES.matcher(apellido2).matches()) {
            errores.add(messageSource.getMessage("error.apellido2.formato", null, locale));
        }

        // Validar correo
        if (!PATTERN_EMAIL.matcher(correo).matches()) {
            errores.add(messageSource.getMessage("error.correo.invalido", null, locale));
        } else {
            String dominio = extraerDominio(correo);
            if (!DOMINIOS_PERMITIDOS.contains(dominio)) {
                errores.add(messageSource.getMessage("error.correo.dominio", null, locale));
            }
        }

        // Validar formato de contraseña
        if (!PATTERN_CONTRASEÑA.matcher(contraseña).matches()) {
            errores.add(messageSource.getMessage("error.contrasena.formato", null, locale));
        }

        // Validar que las contraseñas coincidan
        if (!contraseña.equals(confirmarContraseña)) {
            errores.add(messageSource.getMessage("error.contrasenas.nocoinciden", null, locale));
        }

        // Validar si el usuario ya existe
        if (existeUsuario(identificacion)) {
            errores.add(messageSource.getMessage("error.usuario.existe", null, locale));
        }

        // Validar si el correo ya existe
        if (existeUsuarioPorCorreo(correo)) {
            errores.add(messageSource.getMessage("error.correo.existe", null, locale));
        }

        System.out.println("VALIDACIÓN COMPLETADA. Errores encontrados: " + errores.size());
        return errores;
    }


    @Transactional
    public void guardarUsuario(RegistroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setIdentificacion(dto.getIdentificacion());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido1(dto.getApellido1());
        usuario.setApellido2(dto.getApellido2().isEmpty() ? null : dto.getApellido2());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContraseña()); // Sin encriptar por ahora
        usuario.setIdRol(2); // Rol por defecto
        usuario.setMfaSecret(null); // Asegurar que sea null explícitamente

        System.out.println("INTENTANDO GUARDAR USUARIO EN BD... ID: " + usuario.getIdentificacion());
        System.out.println("DATOS: Nombre=" + usuario.getNombre() + ", Correo=" + usuario.getCorreo() + ", Rol=" + usuario.getIdRol());
        try {
            Usuario guardado = registrarseRepository.save(usuario);
            System.out.println("USUARIO GUARDADO EXITOSAMENTE CON ID: " + guardado.getIdentificacion());
        } catch (Exception e) {
            System.err.println("ERROR CRÍTICO AL GUARDAR USUARIO: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    public boolean existeUsuario(String identificacion) {
        return registrarseRepository.existsByIdentificacion(identificacion);
    }


    public boolean existeUsuarioPorCorreo(String correo) {
        return registrarseRepository.existsByCorreo(correo);
    }


    private String normalizar(String valor) {
        return valor != null ? valor.trim() : "";
    }


    private String extraerDominio(String email) {
        if (email == null || !email.contains("@")) {
            return "";
        }
        String[] partes = email.split("@");
        return partes.length > 1 ? partes[1].toLowerCase() : "";
    }
}