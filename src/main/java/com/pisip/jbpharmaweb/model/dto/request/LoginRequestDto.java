package com.pisip.jbpharmaweb.model.dto.request;

public class LoginRequestDto {
    
    private String correo;
    private String contrasenaHash;

    // 💡 Constructor vacío (Obligatorio para la serialización de JSON)
    public LoginRequestDto() {
    }

    // 💡 Constructor con parámetros (El que te está pidiendo el WebClient)
    public LoginRequestDto(String correo, String contrasenaHash) {
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
    }

    // Getters y Setters manuales
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }
}