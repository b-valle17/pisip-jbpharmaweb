package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.DictamenLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;

public interface IDictamenLoteService {

	public List<DictamenLoteResponseDto> listarDictamenLote();

	public List<EnsayoLaboratorioResponseDto> listarEnsayosPendientes();

	public void guardarDictamenLote(DictamenLoteRequestDto nuevo);

	/** Trae el dictamen junto con los datos del ensayo de laboratorio del mismo lote. */
	public DictamenLoteResponseDto obtenerConEnsayo(int idDictamen);

	public void aceptarDictamen(int idDictamen);

	public void rechazarDictamen(int idDictamen, String motivo);

}