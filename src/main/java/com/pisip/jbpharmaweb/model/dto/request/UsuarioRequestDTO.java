package com.pisip.jbpharmaweb.model.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class UsuarioRequestDTO {

	private int idUsuario;

    private String nombre;

    private String correo;

    private String contrasenaHash;

    private boolean estadoUsuario;
    
    private Date fechaCreacion;
    
    private Integer idRol;
    
    private boolean esNuevo = true; // Por defecto true al crear

}
