package com.pisip.jbpharmaweb.model.dto.request;

import java.sql.Date;

import lombok.Data;

@Data
public class DictamenLoteRequestDto {

	private Integer idOrdenProduccion;

	private Integer idUsuarioInspector;

	private Date fechaDictamen;

	private String estado;

	private String observaciones;
}
