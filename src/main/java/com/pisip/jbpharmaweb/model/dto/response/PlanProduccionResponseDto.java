package com.pisip.jbpharmaweb.model.dto.response;

import java.util.Date;

import lombok.Data;
@Data
public class PlanProduccionResponseDto {
	
	private Integer idPlan;
	private String codigoPlan;
	private Integer mes;
	private Integer anio;
	private Date fechaEmision;
	private String estado;
	private String descripcion;
	
}
