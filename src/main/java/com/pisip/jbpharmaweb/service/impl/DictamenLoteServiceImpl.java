package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.DictamenLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;
import com.pisip.jbpharmaweb.service.IDictamenLoteService;

@Service
public class DictamenLoteServiceImpl implements IDictamenLoteService {

	private final WebClient webClient;

	public DictamenLoteServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<DictamenLoteResponseDto> listarDictamenLote() {
		return webClient.get().uri("/dictamen-lote").retrieve().bodyToFlux(DictamenLoteResponseDto.class)
				.collectList().block();
	}

	@Override
	public void guardarDictamenLote(DictamenLoteRequestDto nuevo) {
		webClient.post().uri("/dictamen-lote").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

}
