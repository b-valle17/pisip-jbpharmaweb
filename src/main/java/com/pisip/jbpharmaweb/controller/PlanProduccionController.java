package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/planproduccion")
public class PlanProduccionController {
	@GetMapping
	public String leerpagina() {
		return "/planproduccion/listarplan"; 
	}
	@GetMapping("/crearplan")
	public String leerpaginacrear() {
		return "/planproduccion/crearplan";
	}
	@GetMapping("/editarplan")
	public String leerpaginaeditar() {
		return "/planproduccion/editarplan";
	}
}
