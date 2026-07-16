package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.ProductoRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;

public interface IProductoService {
	
	public List<ProductoResponseDto> listarProductos();
	
	public void guardarProducto(ProductoRequestDto nuevo);

}
