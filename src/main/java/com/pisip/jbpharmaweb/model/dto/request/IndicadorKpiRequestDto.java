package com.pisip.jbpharmaweb.model.dto.request;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class IndicadorKpiRequestDto {

	private String nombreIndicador;

	private BigDecimal valor;

	private Date fechaCalculo;

	private String descripcion;
}
