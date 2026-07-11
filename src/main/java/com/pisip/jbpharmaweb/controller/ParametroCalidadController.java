package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parametrocalidad")
public class ParametroCalidadController {
	
	@GetMapping
	public String leerpagina() {
		return "/parametrocalidad/listaparametros";
	}
	
	@GetMapping("/crearparametro")
	public String leerpaginacrear() {
		return "/parametrocalidad/crearparametro";
	}
	
	@GetMapping("/editarparametro")
	public String leerpaginaeditar() {
		return "/parametrocalidad/editarparametro";
	}

}
