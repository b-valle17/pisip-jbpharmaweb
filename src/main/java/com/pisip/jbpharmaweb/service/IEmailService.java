package com.pisip.jbpharmaweb.service;

public interface IEmailService {
    void enviarCorreoRestablecimiento(String destinatario, String enlace);
    void enviarAlertaEnsayo(String destinatario, String asunto, String mensaje);
}
