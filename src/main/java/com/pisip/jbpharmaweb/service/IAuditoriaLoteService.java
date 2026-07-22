package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.AuditoriaLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.AuditoriaLoteResponseDto;

public interface IAuditoriaLoteService {

	public List<AuditoriaLoteResponseDto> listarAuditoriaLote();

	public void guardarAuditoriaLote(AuditoriaLoteRequestDto nuevo);

}
