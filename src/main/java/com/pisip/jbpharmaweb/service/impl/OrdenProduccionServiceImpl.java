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

	@Override
	public void eliminarOrden(Integer idOrden) {
		 webClient.delete()
         .uri("/ordenProduccion/{id}", idOrden)
         .retrieve()
         .toBodilessEntity()
         .block();
		
	}

	@Override
	public OrdenProduccionResponseDto buscarPorId(Integer idOrden) {
		 return webClient.get()
		            .uri("/ordenProduccion/{id}", idOrden)
		            .retrieve()
		            .bodyToMono(OrdenProduccionResponseDto.class)
		            .block();
	}

	@Override
	public void actualizarOrden(Integer id, OrdenProduccionRequestDto dto) {
		webClient.put()
        .uri("/ordenProduccion/{id}", id)
        .bodyValue(dto)
        .retrieve()
        .toBodilessEntity()
        .block();
	}
	
}
