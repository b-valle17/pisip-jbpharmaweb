package com.pisip.jbpharmaweb.model.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class PlanProduccionResponseDto {
	
	private Integer idPlan;
	private Integer idUsuario;
	private String codigoPlan;
	private Integer mes;
	private Integer anio;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
	private Date fechaEmision;
	private String estado;
	private String descripcion;
	
	private UsuarioResponseDTO usuario;
}
