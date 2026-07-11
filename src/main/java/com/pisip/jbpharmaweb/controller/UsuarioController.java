package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@GetMapping
	public String leerpagina() {
		return "/usuario/listarusuarios";
	}
	
	@GetMapping("/crearusuario")
	public String leerpaginacrear() {
		return "/usuario/crearusuario";
	}
	
	@GetMapping("/editarusuario")
	public String leerpaginaeditar() {
		return "/usuario/editarusuario";
	}

}
