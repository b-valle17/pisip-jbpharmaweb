package com.pisip.jbpharmaweb.model.dto.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PlanProduccionRequestDto {
	

	private Integer idPlan;

	private String codigoPlan;

	private Integer mes;

	private Integer anio;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaEmision;

	private String estado;

	private String descripcion;
}
