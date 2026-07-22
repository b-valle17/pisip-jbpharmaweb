package com.pisip.jbpharmaweb.service;

import java.util.List;

import com.pisip.jbpharmaweb.model.dto.request.IndicadorKpiRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.IndicadorKpiResponseDto;

public interface IIndicadorKpiService {

	public List<IndicadorKpiResponseDto> listarIndicadorKpi();

	public void guardarIndicadorKpi(IndicadorKpiRequestDto nuevo);

}
