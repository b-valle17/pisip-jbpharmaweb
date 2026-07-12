package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nuevoensayo") //URL
public class NuevoEnsayoController {
	
	@GetMapping
	public String leerpagina() {
		return "/nuevoensayo/nuevo-ensayo"; //UBICACIÓN FíSICA DE LA PAGINA
	}

}
