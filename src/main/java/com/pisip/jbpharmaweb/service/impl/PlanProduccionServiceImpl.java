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

	@Override
	public void eliminarPlan(Integer idPlan) {
	    webClient.delete()
	            .uri("/planProduccion/{id}", idPlan)
	            .retrieve()
	            .toBodilessEntity()
	            .block();
	}
	
	@Override
	public PlanProduccionResponseDto buscarPorId(Integer idPlan) {
	    return webClient.get()
	            .uri("/planProduccion/{id}", idPlan)
	            .retrieve()
	            .bodyToMono(PlanProduccionResponseDto.class)
	            .block();
	}

	@Override
	public void actualizarPlan(Integer id, PlanProduccionRequestDto dto) {
	    webClient.put()
	            .uri("/planProduccion/{id}", id)
	            .bodyValue(dto)
	            .retrieve()
	            .toBodilessEntity()
	            .block();
	}
}
