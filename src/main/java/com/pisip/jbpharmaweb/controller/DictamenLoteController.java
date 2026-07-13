package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dictamenLote")
public class DictamenLoteController {

	@GetMapping
	public String leerpagina() {
		return "/dictamenLote/listarDictamenLote";
	}

	@GetMapping("/crearDictamenLote")
	public String leerpaginacrear() {
		return "/dictamenLote/crearDictamenLote";
	}

	@GetMapping("/editarDictamenLote")
	public String leerpaginaeditar() {
		return "/dictamenLote/editarDictamenLote";
	}

}
