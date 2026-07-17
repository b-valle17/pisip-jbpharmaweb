package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.PlanProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.PlanProduccionResponseDto;
import com.pisip.jbpharmaweb.service.IPlanProduccionService;

@Service
public class PlanProduccionServiceImpl implements IPlanProduccionService{

	private final WebClient webClient;

	public PlanProduccionServiceImpl(WebClient webClient) {
		
		this.webClient = webClient;
	}

	@Override
	public List<PlanProduccionResponseDto> listarPlan() {
		return webClient.get().uri("/planProduccion").retrieve().bodyToFlux(PlanProduccionResponseDto.class).collectList()
				.block();
	}

	@Override
	public void guardarPlan(PlanProduccionRequestDto nuevo) {
		webClient.post().uri("/planProduccion").bodyValue(nuevo).retrieve().toBodilessEntity().block();		
	}
}
