package com.pisip.jbpharmaweb.model.dto.response;

import java.sql.Date;

import lombok.Data;

@Data
public class InformeAuditoriaResponseDto {

	private int idInforme;
	private int idAuditoria;
	private Date fechaGeneracion;
	private String formato;
	private String rutaArchivo;
}
