package com.pisip.jbpharmaweb.model.dto.request;

import java.sql.Date;

import lombok.Data;

@Data
public class AuditoriaLoteRequestDto {

	private int idOrdenProduccion;

	private int idUsuarioAuditor;

	private Date fechaAuditoria;

	private String resultado;

	private String observaciones;
}
