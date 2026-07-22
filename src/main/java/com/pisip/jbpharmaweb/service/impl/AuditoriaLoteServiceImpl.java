package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.AuditoriaLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.AuditoriaLoteResponseDto;
import com.pisip.jbpharmaweb.service.IAuditoriaLoteService;

@Service
public class AuditoriaLoteServiceImpl implements IAuditoriaLoteService {

	private final WebClient webClient;

	public AuditoriaLoteServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<AuditoriaLoteResponseDto> listarAuditoriaLote() {
		return webClient.get().uri("/auditoria-lote").retrieve().bodyToFlux(AuditoriaLoteResponseDto.class)
				.collectList().block();
	}

	@Override
	public void guardarAuditoriaLote(AuditoriaLoteRequestDto nuevo) {
		webClient.post().uri("/auditoria-lote").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

}
