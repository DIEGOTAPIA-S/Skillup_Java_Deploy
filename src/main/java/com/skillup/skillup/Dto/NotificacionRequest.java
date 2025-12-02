package com.skillup.skillup.Dto;

import jakarta.validation.constraints.NotBlank;

public class NotificacionRequest {

    @NotBlank(message = "El asunto no puede estar vacío.")
    private String subject;     // Asunto del correo

    @NotBlank(message = "El cuerpo HTML no puede estar vacío.")
    private String htmlBody;    // Cuerpo HTML del correo MASIVO

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }
}