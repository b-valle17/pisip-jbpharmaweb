package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.HistorialLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.HistorialLoteResponseDto;
import com.pisip.jbpharmaweb.service.IHistorialLoteService;

@Service
public class HistorialLoteServiceImpl implements IHistorialLoteService {

	private final WebClient webClient;

	public HistorialLoteServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<HistorialLoteResponseDto> listarHistorialLote() {
		return webClient.get().uri("/historial-lote").retrieve().bodyToFlux(HistorialLoteResponseDto.class)
				.collectList().block();
	}

	@Override
	public void guardarHistorialLote(HistorialLoteRequestDto nuevo) {
		webClient.post().uri("/historial-lote").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

}
