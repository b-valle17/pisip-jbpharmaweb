package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.ParametroCalidadRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ParametroCalidadResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.RolResponseDto;
import com.pisip.jbpharmaweb.service.IParametroCalidadService;

@Service
public class ParametroCalidadServiceImpl implements IParametroCalidadService{

	private final WebClient webClient;
	
	public ParametroCalidadServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<ParametroCalidadResponseDto> listarParametros() {
		// TODO Auto-generated method stub
		return webClient.get().uri("/parametros-calidad").retrieve().bodyToFlux(ParametroCalidadResponseDto.class).collectList()
				.block();
	}

	@Override
	public void guardarParametro(ParametroCalidadRequestDto nuevo) {
		webClient.post().uri("/parametros-calidad").bodyValue(nuevo).retrieve().toBodilessEntity().block();
		
	}

	@Override
	public List<ProductoResponseDto> listarProductos() {
		return webClient.get().uri("/productos").retrieve().bodyToFlux(ProductoResponseDto.class).collectList().block();
	}

}
