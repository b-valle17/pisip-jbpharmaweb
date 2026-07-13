package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ordenproduccion")
public class OrdenProduccionController {
	@GetMapping
	public String leerpagina() {
		return "/ordenproduccion/listarorden"; 
	}
	@GetMapping("/crearorden")
	public String leerpaginacrear() {
		return "/ordenproduccion/crearorden";
	}
	@GetMapping("/editarorden")
	public String leerpaginaeditar() {
		return "/ordenproduccion/editarorden";
	}
}
