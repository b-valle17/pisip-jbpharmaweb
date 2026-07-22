package com.pisip.jbpharmaweb.service.impl;

import com.pisip.jbpharmaweb.service.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;
    private final String remitente;
    private final String clave;

    public EmailServiceImpl(
            JavaMailSender mailSender,
            @Value("${spring.mail.username:}") String remitente,
            @Value("${spring.mail.password:}") String clave) {
        this.mailSender = mailSender;
        this.remitente = remitente;
        this.clave = clave;
    }

    @Override
    public void enviarCorreoRestablecimiento(String destinatario, String enlace) {
        enviar(destinatario,
                "Restablecimiento de Contraseña - Pisip JBPharma",
                "Hola,\n\nHas solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para continuar:\n\n"
                        + enlace + "\n\nSi no solicitaste este cambio, puedes ignorar este mensaje.");
    }

    @Override
    public void enviarAlertaEnsayo(String destinatario, String asunto, String mensaje) {
        enviar(destinatario, asunto, mensaje);
    }

    private void enviar(String destinatario, String asunto, String contenido) {
        if (remitente == null || remitente.isBlank() || clave == null || clave.isBlank()) {
            throw new IllegalStateException(
                    "El correo no está configurado. Define MAIL_USERNAME y MAIL_PASSWORD con una contraseña de aplicación de Gmail y reinicia jbpharmaweb.");
        }
        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("El destinatario del correo es obligatorio.");
        }

        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setFrom(remitente);
        correo.setTo(destinatario.trim());
        correo.setSubject(asunto == null || asunto.isBlank() ? "Alerta de ensayo" : asunto.trim());
        correo.setText(contenido == null ? "" : contenido);
        mailSender.send(correo);
    }
}
