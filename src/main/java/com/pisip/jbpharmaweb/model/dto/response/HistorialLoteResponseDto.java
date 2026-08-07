package com.pisip.jbpharmaweb.model.dto.response;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HistorialLoteResponseDto {

	private int idHistorial;
	private int idOrdenProduccion;
	private LocalDateTime fechaEvento;
	private String accion;
	private String descripcion;

	private String estadoLote;
}