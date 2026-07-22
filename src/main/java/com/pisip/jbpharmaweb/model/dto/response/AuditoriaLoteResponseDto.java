package com.pisip.jbpharmaweb.model.dto.response;

import java.sql.Date;

import lombok.Data;

@Data
public class AuditoriaLoteResponseDto {

	private int idAuditoria;
	private int idOrdenProduccion;
	private int idUsuarioAuditor;
	private Date fechaAuditoria;
	private String resultado;
	private String observaciones;
}
