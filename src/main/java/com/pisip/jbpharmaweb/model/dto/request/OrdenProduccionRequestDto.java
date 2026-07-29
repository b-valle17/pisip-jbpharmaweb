package com.pisip.jbpharmaweb.model.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm")
	private LocalDateTime fechaInicio;
	@DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm")
	private LocalDateTime fechaFin;

	private String estado;
}
