package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producto")
public class ProductoController {
	
	@GetMapping
	public String leerpagina() {
		return "/producto/listarproductos";
	}
	
	@GetMapping("/crearproducto")
	public String leerpaginacrear() {
		return "/producto/crearproducto";
	}
	
	@GetMapping("/editarproducto")
	public String leerpaginaeditar() {
		return "/producto/editarproducto";
	}

}
