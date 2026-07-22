package com.pisip.jbpharmaweb.model.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
	
	private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasenaHash;
    private boolean estadoUsuario;
    private Date fechaCreacion;
    private Integer idRol;
}