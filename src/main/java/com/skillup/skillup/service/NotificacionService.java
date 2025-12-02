package com.skillup.skillup.service;


import com.skillup.skillup.Dto.NotificacionRequest;
import com.skillup.skillup.Dto.Response.ApiResponse;
import com.skillup.skillup.model.Usuarios;
import com.skillup.skillup.repository.UsuariosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    private final UsuariosRepository usuariosRepository;
    private final EmailService emailService;

    public NotificacionService(UsuariosRepository usuariosRepository,
                               EmailService emailService) {
        this.usuariosRepository = usuariosRepository;
        this.emailService = emailService;
    }


    public ApiResponse<String> sendNotificationToAllClients(NotificacionRequest request) {

        ApiResponse<String> response = new ApiResponse<>();

        try {

            int rolEstudiante = 2;

            List<Usuarios> estudiantes = usuariosRepository.findByIdRol(rolEstudiante);

            for (Usuarios estud : estudiantes) {

                String email = estud.getCorreo();

                if (email != null && !email.isBlank()) {

                    emailService.sendHtmlEmail(
                            email,
                            request.getSubject(),
                            request.getHtmlBody()
                    );
                }
            }

            response.setHttpStatusCode(200);
            response.setMessage("Correos enviados a " + estudiantes.size() + " estudiantes.");
            response.setData("OK");

        } catch (Exception ex) {

            response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error enviando correos: " + ex.getMessage());
            response.setData("");
            response.setTotalRecords(0);
        }

        return response;
    }
}