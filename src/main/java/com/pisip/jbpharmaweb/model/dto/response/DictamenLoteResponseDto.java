package com.pisip.jbpharmaweb.model.dto.response;

import java.sql.Date;

import lombok.Data;

@Data
public class DictamenLoteResponseDto {

	private int idDictamen;
	private int idOrdenProduccion;
	private int idUsuarioInspector;
	private Date fechaDictamen;
	private String estado;
	private String observaciones;
}
