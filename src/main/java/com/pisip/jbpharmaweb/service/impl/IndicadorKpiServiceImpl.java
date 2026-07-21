package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.IndicadorKpiRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.IndicadorKpiResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.IndicadorKpiResumenDto;
import com.pisip.jbpharmaweb.service.IIndicadorKpiService;

@Service
public class IndicadorKpiServiceImpl implements IIndicadorKpiService {

	private final WebClient webClient;

	public IndicadorKpiServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<IndicadorKpiResponseDto> listarIndicadorKpi() {
		return webClient.get().uri("/indicador-kpi").retrieve().bodyToFlux(IndicadorKpiResponseDto.class)
				.collectList().block();
	}

	@Override
	public void guardarIndicadorKpi(IndicadorKpiRequestDto nuevo) {
		webClient.post().uri("/indicador-kpi").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

	@Override
	public IndicadorKpiResumenDto obtenerResumen() {
		return webClient.get().uri("/indicador-kpi/resumen").retrieve().bodyToMono(IndicadorKpiResumenDto.class)
				.block();
	}

}