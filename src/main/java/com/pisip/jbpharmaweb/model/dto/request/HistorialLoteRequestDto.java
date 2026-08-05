package com.pisip.jbpharmaweb.model.dto.request;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HistorialLoteRequestDto {

	private int idOrdenProduccion;

	private LocalDateTime fechaEvento;

	private String accion;

	private String descripcion;
}
