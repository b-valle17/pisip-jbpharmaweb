package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.InformeAuditoriaRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.InformeAuditoriaResponseDto;

public interface IInformeAuditoriaService {

	public List<InformeAuditoriaResponseDto> listarInformeAuditoria();

	public void guardarInformeAuditoria(InformeAuditoriaRequestDto nuevo);

	public void comentarInformeAuditoria(int idInforme, String comentario);

}
