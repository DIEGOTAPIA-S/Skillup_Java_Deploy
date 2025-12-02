package com.skillup.skillup.config;


import com.skillup.skillup.model.Usuarios;
import com.skillup.skillup.repository.LoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {


        HttpSession session = request.getSession(true);


        String correo = authentication.getName();


        Usuarios usuario = loginRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en BD"));


        session.setAttribute("roles_sistema", usuario.getIdentificacion());
        session.setAttribute("rol_sistema", usuario.getIdRol());
        session.setAttribute("nombre_usuario", usuario.getNombre());
        session.setAttribute("correo_usuario", usuario.getCorreo());


        String rol = authentication.getAuthorities().iterator().next().getAuthority();

        switch (rol) {
            case "ROLE_1":
                response.sendRedirect("/administrador/seleccionar");
                break;
            case "ROLE_2":
                response.sendRedirect("/estudiante/lobby");
                break;
            case "ROLE_3":
                response.sendRedirect("/evaluador/inicio");
                break;
            default:
                response.sendRedirect("/login");
        }
    }
}
