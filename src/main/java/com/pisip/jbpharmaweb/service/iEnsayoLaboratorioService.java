package com.pisip.jbpharmaweb.service;

import java.util.List;
import java.util.Optional;
import com.pisip.jbpharmaweb.model.dto.request.EnsayoLaboratorioRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;

public interface iEnsayoLaboratorioService {
	List<EnsayoLaboratorioResponseDto> listar();

	Optional<EnsayoLaboratorioResponseDto> buscarPorId(long id);

	EnsayoLaboratorioResponseDto guardar(EnsayoLaboratorioRequestDto dto);

	EnsayoLaboratorioResponseDto actualizar(long id, EnsayoLaboratorioRequestDto dto);

	void eliminar(long id);
}
