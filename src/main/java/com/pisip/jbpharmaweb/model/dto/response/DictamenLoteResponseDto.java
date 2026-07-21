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

	// Datos del ensayo de laboratorio asociado (nulos si el lote aun no tiene ensayo registrado).
	private Long idEnsayo;
	private String codigoEnsayo;
	private String estadoEnsayo;
	private String observacionEnsayo;
}
