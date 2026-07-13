package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auditoriaLote")
public class AuditoriaLoteController {

	@GetMapping
	public String leerpagina() {
		return "/auditoriaLote/listarAuditoriaLote";
	}

	@GetMapping("/crearAuditoriaLote")
	public String leerpaginacrear() {
		return "/auditoriaLote/crearAuditoriaLote";
	}

	@GetMapping("/editarAuditoriaLote")
	public String leerpaginaeditar() {
		return "/auditoriaLote/editarAuditoriaLote";
	}

}
