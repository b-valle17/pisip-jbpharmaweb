package com.pisip.jbpharmaweb.model.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioResponseDTO {
	
	private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasenaHash;
    private boolean estadoUsuario;
    private Date fechaCreacion;
    private RolResponseDto idRol;

    // Constructor vacío obligatorio
    public UsuarioResponseDTO() {
    }

    // --- GETTERS Y SETTERS MANUALES ---

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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

    public boolean isEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(boolean estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @JsonProperty("fkRol")
    public RolResponseDto getIdRol() {
        return idRol;
    }

    @JsonProperty("fkRol")
    public void setIdRol(RolResponseDto idRol) {
        this.idRol = idRol;
    }
}