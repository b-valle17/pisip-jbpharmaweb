package com.pisip.jbpharmaweb.service.impl;

import java.util.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisip.jbpharmaweb.model.dto.request.ValidacionSemaforicaRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ValidacionSemaforicaResponseDto;
import com.pisip.jbpharmaweb.service.iValidacionSemaforicaService;

@Service
public class ValidacionSemaforicaServiceImpl implements iValidacionSemaforicaService {
	private final WebClient webClient;

	public ValidacionSemaforicaServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	public List<ValidacionSemaforicaResponseDto> listar() {
		return webClient.get().uri("/validaciones").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<ValidacionSemaforicaResponseDto>>() {
				}).blockOptional().orElseGet(List::of);
	}

	public Optional<ValidacionSemaforicaResponseDto> buscarPorId(long id) {
		return webClient.get().uri("/validaciones/{id}", id).retrieve()
				.bodyToMono(ValidacionSemaforicaResponseDto.class).blockOptional();
	}

	public ValidacionSemaforicaResponseDto guardar(ValidacionSemaforicaRequestDto dto) {
		return webClient.post().uri("/validaciones").bodyValue(dto).retrieve()
				.bodyToMono(ValidacionSemaforicaResponseDto.class).block();
	}

	public ValidacionSemaforicaResponseDto actualizar(long id, ValidacionSemaforicaRequestDto dto) {
		return webClient.put().uri("/validaciones/{id}", id).bodyValue(dto).retrieve()
				.bodyToMono(ValidacionSemaforicaResponseDto.class).block();
	}

	public void eliminar(long id) {
		webClient.delete().uri("/validaciones/{id}", id).retrieve().toBodilessEntity().block();
	}
}
