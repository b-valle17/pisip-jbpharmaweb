package com.pisip.jbpharmaweb.model.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ParametroCalidadRequestDto {

	private Integer idParametro;
	
	private String nombreParametro;

	private BigDecimal limiteMinimo;

	private BigDecimal limiteMaximo;

	private String unidadMedida;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaConfiguracion;
	
	private Integer idProducto;
}