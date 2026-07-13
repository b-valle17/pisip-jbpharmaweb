package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/indicadorKpi")
public class IndicadorKpiController {

	@GetMapping
	public String leerpagina() {
		return "/indicadorKpi/listarIndicadorKpi";
	}

	@GetMapping("/crearIndicadorKpi")
	public String leerpaginacrear() {
		return "/indicadorKpi/crearIndicadorKpi";
	}

	@GetMapping("/editarIndicadorKpi")
	public String leerpaginaeditar() {
		return "/indicadorKpi/editarIndicadorKpi";
	}

}
