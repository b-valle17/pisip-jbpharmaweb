package com.pisip.jbpharmaweb.service.impl;

import java.util.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisip.jbpharmaweb.model.dto.request.EnsayoLaboratorioRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;
import com.pisip.jbpharmaweb.service.iEnsayoLaboratorioService;

@Service
public class EnsayoLaboratorioServiceImpl implements iEnsayoLaboratorioService {
	private final WebClient webClient;

	public EnsayoLaboratorioServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	public List<EnsayoLaboratorioResponseDto> listar() {
		return webClient.get().uri("/api/ensayos").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<EnsayoLaboratorioResponseDto>>() {
				}).blockOptional().orElseGet(List::of);
	}

	public Optional<EnsayoLaboratorioResponseDto> buscarPorId(long id) {
		return webClient.get().uri("/api/ensayos/{id}", id).retrieve().bodyToMono(EnsayoLaboratorioResponseDto.class)
				.blockOptional();
	}

	public EnsayoLaboratorioResponseDto guardar(EnsayoLaboratorioRequestDto dto) {
		return webClient.post().uri("/api/ensayos").bodyValue(dto).retrieve().bodyToMono(EnsayoLaboratorioResponseDto.class)
				.block();
	}

	public EnsayoLaboratorioResponseDto actualizar(long id, EnsayoLaboratorioRequestDto dto) {
		return webClient.put().uri("/api/ensayos/{id}", id).bodyValue(dto).retrieve()
				.bodyToMono(EnsayoLaboratorioResponseDto.class).block();
	}

	public void eliminar(long id) {
		webClient.delete().uri("/api/ensayos/{id}", id).retrieve().toBodilessEntity().block();
	}
}
