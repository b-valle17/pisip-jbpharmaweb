package com.pisip.jbpharmaweb.service.impl;

import java.util.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisip.jbpharmaweb.model.dto.request.EnsayoVariableRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoVariableResponseDto;
import com.pisip.jbpharmaweb.service.iEnsayoVariableService;

@Service
public class EnsayoVariableServiceImpl implements iEnsayoVariableService {
	private final WebClient webClient;

	public EnsayoVariableServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	public List<EnsayoVariableResponseDto> listar() {
		return webClient.get().uri("/api/variables").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<EnsayoVariableResponseDto>>() {
				}).blockOptional().orElseGet(List::of);
	}

	public Optional<EnsayoVariableResponseDto> buscarPorId(long id) {
		return webClient.get().uri("/api/variables/{id}", id).retrieve().bodyToMono(EnsayoVariableResponseDto.class)
				.blockOptional();
	}

	public EnsayoVariableResponseDto guardar(EnsayoVariableRequestDto dto) {
		return webClient.post().uri("/api/variables").bodyValue(dto).retrieve().bodyToMono(EnsayoVariableResponseDto.class)
				.block();
	}

	public EnsayoVariableResponseDto actualizar(long id, EnsayoVariableRequestDto dto) {
		return webClient.put().uri("/api/variables/{id}", id).bodyValue(dto).retrieve()
				.bodyToMono(EnsayoVariableResponseDto.class).block();
	}

	public void eliminar(long id) {
		webClient.delete().uri("/api/variables/{id}", id).retrieve().toBodilessEntity().block();
	}
}
