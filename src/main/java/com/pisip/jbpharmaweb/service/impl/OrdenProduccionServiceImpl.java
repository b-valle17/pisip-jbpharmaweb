package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.OrdenProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;
import com.pisip.jbpharmaweb.service.IOrdenProduccionService;

@Service
public class OrdenProduccionServiceImpl implements IOrdenProduccionService{
	
	private final WebClient webClient;

	public OrdenProduccionServiceImpl(WebClient webClient) {

		this.webClient = webClient;
	}

	@Override
	public List<OrdenProduccionResponseDto> listarOrden() {
		
		return webClient.get().uri("/ordenProduccion").retrieve().bodyToFlux(OrdenProduccionResponseDto.class).collectList()
				.block();
	}

	@Override
	public void guardarOrden(OrdenProduccionRequestDto nuevo) {
		webClient.post().uri("/ordenProduccion").bodyValue(nuevo).retrieve().toBodilessEntity().block();			
	}
	
}
