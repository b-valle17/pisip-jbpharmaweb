package com.pisip.jbpharmaweb.model.dto.response;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class IndicadorKpiResumenDto {

	private BigDecimal cumplimientoPlanMensual;
	private long totalLotesProducidos;
	private long lotesEnCuarentena;
	private Map<String, Long> distribucionPorEstado;
	private long dictamenesAceptados;
	private long dictamenesRechazados;
	private long dictamenesPendientes;
	private BigDecimal tasaAprobacionDictamenes;
}