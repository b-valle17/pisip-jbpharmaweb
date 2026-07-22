package com.pisip.jbpharmaweb.service.impl;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Optional<ProductoResponseDto> obtenerProductoPorId(int idProducto) {
		try {
			ProductoResponseDto producto = webClient.get()
	            .uri("/productos/{idProducto}", idProducto)
	            .retrieve()
	            .bodyToMono(ProductoResponseDto.class)
	            .block();
	        return Optional.ofNullable(producto);
	    } catch (Exception e) {
	        return Optional.empty();
	    }
	}

	@Override
	public void actualizarProducto(int idProducto, ProductoRequestDto productoActualizado) {
		webClient.put()
        .uri("/productos/{idProducto}", idProducto)
        .bodyValue(productoActualizado)
        .retrieve()
        .toBodilessEntity()
        .block();
		
	}

	@Override
	public void eliminarProducto(int idProducto) {
		webClient.delete()
        .uri("/productos/{idProducto}", idProducto)
        .retrieve()
        .toBodilessEntity() // Usamos esto porque el backend responde con un "Void" (vacío)
        .block(); // Espera a que la operación termine de forma síncrona
		
	}

}
