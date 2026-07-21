package com.pisip.jbpharmaweb.model.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class OrdenProduccionRequestDto {

	private Integer idOrden;
	
	private Integer idPlan;

	private Integer idProducto;
	
	private Integer idUsuario;

	private String numeroLote;

	private BigDecimal cantidadLote;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;

	private String estado;
}
