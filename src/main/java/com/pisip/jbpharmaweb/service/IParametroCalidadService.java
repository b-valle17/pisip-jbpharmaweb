package com.pisip.jbpharmaweb.service;

import java.util.List;
import java.util.Optional;

import com.pisip.jbpharmaweb.model.dto.request.ParametroCalidadRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ParametroCalidadResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;

public interface IParametroCalidadService {

	public List<ParametroCalidadResponseDto> listarParametros();

	public void guardarParametro(ParametroCalidadRequestDto nuevo);

	List<ProductoResponseDto> listarProductos();

	Optional<ParametroCalidadResponseDto> obtenerParametroPorId(int idParametro);

	void actualizarParametro(int idParametro, ParametroCalidadRequestDto parametroActualizado);

	public void eliminarParametro(int idParametro);

}
