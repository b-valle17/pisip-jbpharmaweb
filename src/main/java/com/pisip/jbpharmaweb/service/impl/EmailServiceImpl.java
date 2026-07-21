package com.pisip.jbpharmaweb.service.impl;

import com.pisip.jbpharmaweb.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreoRestablecimiento(String destinatario, String enlace) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Restablecimiento de Contraseña - Pisip JBPharma");
        mensaje.setText("Hola,\n\nHas solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para continuar:\n\n" 
                + enlace + "\n\nSi no solicitaste este cambio, puedes ignorar este mensaje.");

        mailSender.send(mensaje);
    }
}