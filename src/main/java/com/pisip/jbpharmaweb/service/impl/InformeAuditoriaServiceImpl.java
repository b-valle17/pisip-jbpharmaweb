package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.InformeAuditoriaRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.InformeAuditoriaResponseDto;
import com.pisip.jbpharmaweb.service.IInformeAuditoriaService;

@Service
public class InformeAuditoriaServiceImpl implements IInformeAuditoriaService {

	private final WebClient webClient;

	public InformeAuditoriaServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<InformeAuditoriaResponseDto> listarInformeAuditoria() {
		return webClient.get().uri("/informe-auditoria").retrieve().bodyToFlux(InformeAuditoriaResponseDto.class)
				.collectList().block();
	}

	@Override
	public void guardarInformeAuditoria(InformeAuditoriaRequestDto nuevo) {
		webClient.post().uri("/informe-auditoria").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

}
