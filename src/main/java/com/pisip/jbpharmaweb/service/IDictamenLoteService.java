package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.DictamenLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;

public interface IDictamenLoteService {

	public List<DictamenLoteResponseDto> listarDictamenLote();

	public void guardarDictamenLote(DictamenLoteRequestDto nuevo);

}
