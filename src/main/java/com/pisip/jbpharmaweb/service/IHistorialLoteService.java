package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.HistorialLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.HistorialLoteResponseDto;

public interface IHistorialLoteService {

	public List<HistorialLoteResponseDto> listarHistorialLote();

	public void guardarHistorialLote(HistorialLoteRequestDto nuevo);

	/** Lista solo los lotes cuyo dictamen ya fue ACEPTADO o RECHAZADO. estado es opcional (null = ambos). */
	public List<HistorialLoteResponseDto> listarFinalizados(String estado);

}