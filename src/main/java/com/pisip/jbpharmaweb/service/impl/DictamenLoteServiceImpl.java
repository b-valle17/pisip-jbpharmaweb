package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.DictamenLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.request.DictamenRechazoRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;
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
	public List<EnsayoLaboratorioResponseDto> listarEnsayosPendientes() {
		return webClient.get().uri("/dictamen-lote/pendientes").retrieve()
				.bodyToFlux(EnsayoLaboratorioResponseDto.class).collectList().block();
	}

	@Override
	public void guardarDictamenLote(DictamenLoteRequestDto nuevo) {
		webClient.post().uri("/dictamen-lote").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

	@Override
	public DictamenLoteResponseDto obtenerConEnsayo(int idDictamen) {
		return webClient.get().uri("/dictamen-lote/{id}/ensayo", idDictamen).retrieve()
				.bodyToMono(DictamenLoteResponseDto.class).block();
	}

	@Override
	public void aceptarDictamen(int idDictamen) {
		webClient.put().uri("/dictamen-lote/{id}/aceptar", idDictamen).retrieve().toBodilessEntity().block();
	}

	@Override
	public void rechazarDictamen(int idDictamen, String motivo) {
		DictamenRechazoRequestDto body = new DictamenRechazoRequestDto();
		body.setMotivo(motivo);
		webClient.put().uri("/dictamen-lote/{id}/rechazar", idDictamen).bodyValue(body).retrieve()
				.toBodilessEntity().block();
	}

}