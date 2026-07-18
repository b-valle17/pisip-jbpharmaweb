package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.HistorialLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.HistorialLoteResponseDto;

public interface IHistorialLoteService {

	public List<HistorialLoteResponseDto> listarHistorialLote();

	public void guardarHistorialLote(HistorialLoteRequestDto nuevo);

}
