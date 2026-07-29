package com.pisip.jbpharmaweb.model.dto.request;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PlanProduccionRequestDto {
	

	private Integer idPlan;
	
	private Integer idUsuario;

	private String codigoPlan;

	private Integer mes;

	private Integer anio;
	@DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm")
	private LocalDateTime fechaEmision;

	private String estado;

	private String descripcion;
	
	private Integer cantidadLotesEstimada;
}
