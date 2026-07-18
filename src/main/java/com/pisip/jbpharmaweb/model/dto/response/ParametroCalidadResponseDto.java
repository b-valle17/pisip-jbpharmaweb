package com.pisip.jbpharmaweb.model.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ParametroCalidadResponseDto {

	private int idParametro;
	private String nombreParametro;
	private BigDecimal limiteMinimo;
	private BigDecimal limiteMaximo;
	private String unidadMedida;
	private Date fechaConfiguracion;
	private int idProducto;
}