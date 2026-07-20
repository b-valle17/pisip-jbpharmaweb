package com.pisip.jbpharmaweb.model.dto.request;

import java.sql.Date;

import lombok.Data;

@Data
public class DictamenLoteRequestDto {

	private int idOrdenProduccion;

	private int idUsuarioInspector;

	private Date fechaDictamen;

	private String estado;

	private String observaciones;
}
