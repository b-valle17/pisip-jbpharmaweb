package com.pisip.jbpharmaweb.model.dto.response;

import lombok.Data;

@Data
public class LoginResponseDto {

	private boolean autenticado;
	private String mensaje;
	private UsuarioResponseDTO usuario;
}