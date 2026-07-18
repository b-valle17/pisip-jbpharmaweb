package com.pisip.jbpharmaweb.service;

import java.util.List;
import java.util.Optional;

import com.pisip.jbpharmaweb.model.dto.request.ProductoRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;

public interface IProductoService {

	public List<ProductoResponseDto> listarProductos();

	public void guardarProducto(ProductoRequestDto nuevo);

	Optional<ProductoResponseDto> obtenerProductoPorId(int idProducto);

	void actualizarProducto(int idProducto, ProductoRequestDto productoActualizado);

	public void eliminarProducto(int idProducto);

}
