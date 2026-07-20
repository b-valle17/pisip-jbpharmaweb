package com.pisip.jbpharmaweb.model.dto.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PlanProduccionRequestDto {
	

	private int idPlan;

	private String codigoPlan;

	private int mes;

	private int anio;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaEmision;

	private String estado;

	private String descripcion;
}
