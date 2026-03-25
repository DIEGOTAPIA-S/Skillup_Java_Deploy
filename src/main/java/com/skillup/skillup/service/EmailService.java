package com.skillup.skillup.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    @Autowired(required = false)
    private JavaMailSender mailSender;

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
        // PRIORIDAD 1: Si hay API Key de Resend, usarla (Ideal para Render/Nube)
        if (resendApiKey != null && !resendApiKey.isEmpty()) {
            sendViaResend(to, subject, htmlBody);
        } 
        // PRIORIDAD 2: Si no hay API Key pero hay MailSender configurado, usar SMTP (Ideal para Local/Railway)
        else if (mailSender != null) {
            sendViaSmtp(to, subject, htmlBody);
        } 
        else {
            System.err.println("ERROR: No hay método de envío configurado (Resend o SMTP).");
        }
    }

    private void sendViaResend(String to, String subject, String htmlBody) {
        try {
            System.out.println("Enviando vía RESEND API a: " + to);
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
            System.out.println("¡Éxito con Resend!");
        } catch (Exception e) {
            System.err.println("Error Resend: " + e.getMessage());
        }
    }

    private void sendViaSmtp(String to, String subject, String htmlBody) {
        try {
            System.out.println("Enviando vía SMTP (Gmail/Outlook) a: " + to);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
            System.out.println("¡Éxito con SMTP!");
        } catch (Exception e) {
            System.err.println("Error SMTP: " + e.getMessage());
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