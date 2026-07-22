package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.PlanProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.PlanProduccionResponseDto;

public interface IPlanProduccionService {
	
	public List<PlanProduccionResponseDto> listarPlan();
	
	public void guardarPlan(PlanProduccionRequestDto nuevo);
	
	void eliminarPlan(Integer idPlan);
	
	PlanProduccionResponseDto buscarPorId(Integer idPlan); 
	
    void actualizarPlan(Integer id, PlanProduccionRequestDto dto);

}
