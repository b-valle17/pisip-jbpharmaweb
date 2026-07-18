package com.pisip.jbpharmaweb.model.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class IndicadorKpiResponseDto {

	private int idKpi;
	private String nombreIndicador;
	private BigDecimal valor;
	private Date fechaCalculo;
	private String descripcion;
}
