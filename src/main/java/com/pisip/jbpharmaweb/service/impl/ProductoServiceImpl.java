package com.pisip.jbpharmaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisip.jbpharmaweb.model.dto.request.ProductoRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService{

	private final WebClient webClient;
	
	public ProductoServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<ProductoResponseDto> listarProductos() {
		// TODO Auto-generated method stub
		return webClient.get().uri("/productos").retrieve().bodyToFlux(ProductoResponseDto.class).collectList()
				.block();
	}

	@Override
	public void guardarProducto(ProductoRequestDto nuevo) {
		webClient.post().uri("/productos").bodyValue(nuevo).retrieve().toBodilessEntity().block();
		
	}

}
