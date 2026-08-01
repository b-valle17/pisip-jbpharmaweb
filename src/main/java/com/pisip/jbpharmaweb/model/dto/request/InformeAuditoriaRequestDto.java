package com.pisip.jbpharmaweb.model.dto.request;

import java.sql.Date;

import lombok.Data;

@Data
public class InformeAuditoriaRequestDto {

	private int idAuditoria;

	private Date fechaGeneracion;

	private String formato;

	private String rutaArchivo;

	private String comentario;
}
