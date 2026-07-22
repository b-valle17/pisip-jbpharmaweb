package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.IndicadorKpiRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.IndicadorKpiResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.IndicadorKpiResumenDto;
import com.pisip.jbpharmaweb.service.IIndicadorKpiService;

@Controller
@RequestMapping("/indicadorKpi")
public class IndicadorKpiController {

	@Autowired
	private IIndicadorKpiService servicioAPI;

	@GetMapping
	public String leerpagina(Model model) {
		List<IndicadorKpiResponseDto> datosAPI = servicioAPI.listarIndicadorKpi();
		model.addAttribute("listaindicadorkpi", datosAPI);

		// Metricas para los SmallBox/InfoBox del dashboard.
		IndicadorKpiResumenDto resumen = servicioAPI.obtenerResumen();
		model.addAttribute("resumen", resumen);

		return "/indicadorKpi/listarIndicadorKpi";
	}

	@GetMapping("/crearIndicadorKpi")
	public String leerpaginacrear(Model model) {
		model.addAttribute("indicadorKpi", new IndicadorKpiRequestDto());
		return "/indicadorKpi/crearIndicadorKpi";
	}

	@PostMapping("/guardar")
	public String guardarIndicadorKpi(@ModelAttribute IndicadorKpiRequestDto indicadorKpi) {
		servicioAPI.guardarIndicadorKpi(indicadorKpi);
		return "redirect:/indicadorKpi";
	}

	@GetMapping("/editarIndicadorKpi")
	public String leerpaginaeditar() {
		return "/indicadorKpi/editarIndicadorKpi";
	}

}