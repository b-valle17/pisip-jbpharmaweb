package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.OrdenProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;

public interface IOrdenProduccionService {

	public List<OrdenProduccionResponseDto> listarOrden();
	
	public void guardarOrden(OrdenProduccionRequestDto nuevo);
	
	void eliminarOrden(Integer idOrden);
	
	OrdenProduccionResponseDto buscarPorId(Integer idOrden);
	
    void actualizarOrden(Integer idOrden, OrdenProduccionRequestDto dto);
}
