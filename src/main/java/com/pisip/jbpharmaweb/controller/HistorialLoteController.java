package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/historialLote")
public class HistorialLoteController {

	@GetMapping
	public String leerpagina() {
		return "/historialLote/listarHistorialLote";
	}

	@GetMapping("/crearHistorialLote")
	public String leerpaginacrear() {
		return "/historialLote/crearHistorialLote";
	}

	@GetMapping("/editarHistorialLote")
	public String leerpaginaeditar() {
		return "/historialLote/editarHistorialLote";
	}

}
