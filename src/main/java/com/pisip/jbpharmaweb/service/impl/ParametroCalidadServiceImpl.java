package com.pisip.jbpharmaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.ParametroCalidadRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ParametroCalidadResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.RolResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
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

	@Override
	public Optional<ParametroCalidadResponseDto> obtenerParametroPorId(int idParametro) {
		try {
			ParametroCalidadResponseDto parametro = webClient.get()
	            .uri("/parametros-calidad/{idParametro}", idParametro)
	            .retrieve()
	            .bodyToMono(ParametroCalidadResponseDto.class)
	            .block();
	        return Optional.ofNullable(parametro);
	    } catch (Exception e) {
	        return Optional.empty();
	    }
	}

	@Override
	public void actualizarParametro(int idParametro, ParametroCalidadRequestDto parametroActualizado) {
		webClient.put()
        .uri("/parametros-calidad/{idParametro}", idParametro)
        .bodyValue(parametroActualizado)
        .retrieve()
        .toBodilessEntity()
        .block();
		
	}

	@Override
	public void eliminarParametro(int idParametro) {
		webClient.delete()
        .uri("/parametros-calidad/{idParametro}", idParametro)
        .retrieve()
        .toBodilessEntity() // Usamos esto porque el backend responde con un "Void" (vacío)
        .block(); // Espera a que la operación termine de forma síncrona
		
	}

}
