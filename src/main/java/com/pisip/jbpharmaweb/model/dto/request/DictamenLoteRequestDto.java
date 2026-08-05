package com.pisip.jbpharmaweb.model.dto.request;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

@Data
public class DictamenLoteRequestDto {

	private Integer idOrdenProduccion;

	private Integer idUsuarioInspector;

	private LocalDate fechaDictamen;

	private String estado;

	private String observaciones;
}
