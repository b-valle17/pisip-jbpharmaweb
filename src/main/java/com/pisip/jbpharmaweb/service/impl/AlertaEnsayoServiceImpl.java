package com.pisip.jbpharmaweb.service.impl;

import java.util.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisip.jbpharmaweb.model.dto.request.AlertaEnsayoRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.AlertaEnsayoResponseDto;
import com.pisip.jbpharmaweb.service.iAlertaEnsayoService;

@Service
public class AlertaEnsayoServiceImpl implements iAlertaEnsayoService {
	private final WebClient webClient;

	public AlertaEnsayoServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	public List<AlertaEnsayoResponseDto> listar() {
		return webClient.get().uri("/alertas").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<AlertaEnsayoResponseDto>>() {
				}).blockOptional().orElseGet(List::of);
	}

	public Optional<AlertaEnsayoResponseDto> buscarPorId(long id) {
		return webClient.get().uri("/alertas/{id}", id).retrieve().bodyToMono(AlertaEnsayoResponseDto.class)
				.blockOptional();
	}

	public AlertaEnsayoResponseDto guardar(AlertaEnsayoRequestDto dto) {
		return webClient.post().uri("/alertas").bodyValue(dto).retrieve().bodyToMono(AlertaEnsayoResponseDto.class)
				.block();
	}

	public AlertaEnsayoResponseDto actualizar(long id, AlertaEnsayoRequestDto dto) {
		return webClient.put().uri("/alertas/{id}", id).bodyValue(dto).retrieve()
				.bodyToMono(AlertaEnsayoResponseDto.class).block();
	}

	public void eliminar(long id) {
		webClient.delete().uri("/alertas/{id}", id).retrieve().toBodilessEntity().block();
	}
}
