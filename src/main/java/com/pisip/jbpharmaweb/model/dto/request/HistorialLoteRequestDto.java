package com.pisip.jbpharmaweb.model.dto.request;

import java.sql.Date;

import lombok.Data;

@Data
public class HistorialLoteRequestDto {

	private int idOrdenProduccion;

	private Date fechaEvento;

	private String accion;

	private String descripcion;
}
