package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detalleensayo") //URL
public class DetalleEnsayoController {
	
	@GetMapping
	public String leerpagina() {
		return "/detalleensayo/detalle-ensayo"; //UBICACIÓN FíSICA DE LA PAGINA
	}

}
