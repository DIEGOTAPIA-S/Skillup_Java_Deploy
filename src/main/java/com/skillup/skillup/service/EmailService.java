package com.skillup.skillup.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY:}")
    private String resendApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String loadHtmlTemplate(String templateName) {
        try {
            ClassPathResource resource =
                    new ClassPathResource("templates/correos/" + templateName + ".html");
            
            try (java.io.InputStream inputStream = resource.getInputStream()) {
                byte[] bytes = inputStream.readAllBytes();
                return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar la plantilla: " + templateName, e);
        }
    }

    @Async
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        if (resendApiKey == null || resendApiKey.isEmpty()) {
            System.err.println("ERROR: RESEND_API_KEY no configurada en Render. No se puede enviar correo.");
            return;
        }

        try {
            System.out.println("Enviando correo vía Resend API a: " + to);

            String url = "https://api.resend.com/emails";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(resendApiKey);

            Map<String, Object> body = new HashMap<>();
            body.put("from", "SkillUp <onboarding@resend.dev>");
            body.put("to", Collections.singletonList(to));
            body.put("subject", subject);
            body.put("html", htmlBody);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, request, String.class);

            System.out.println("¡Correo enviado con éxito vía API!");

        } catch (Exception e) {
            System.err.println("Error al enviar correo vía Resend: " + e.getMessage());
        }
    }

    public void sendWelcomeEmail(String to, String nombreUsuario) {
        String html = loadHtmlTemplate("bienvenida");
        html = html.replace("{{nombre}}", nombreUsuario);
        sendHtmlEmail(to, "¡Bienvenido a SkillUp! 🎉", html);
    }

    public void sendResetPasswordEmail(String to, String resetLink) {
        String html = loadHtmlTemplate("restablecer");
        html = html.replace("{{link}}", resetLink);
        sendHtmlEmail(to, "Restablecer tu contraseña - SkillUp", html);
    }
}