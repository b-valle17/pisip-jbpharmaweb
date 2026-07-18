package com.pisip.jbpharmaweb.model.dto.response;

import java.sql.Date;

import lombok.Data;

@Data
public class HistorialLoteResponseDto {

	private int idHistorial;
	private int idOrdenProduccion;
	private Date fechaEvento;
	private String accion;
	private String descripcion;
}
