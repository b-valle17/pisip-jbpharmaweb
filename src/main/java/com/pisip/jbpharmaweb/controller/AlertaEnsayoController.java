package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alertas") //URL
public class AlertaEnsayoController {
	
	@GetMapping
	public String leerpagina() {
		return "/alertaensayo/alertas"; //UBICACIÓN FíSICA DE LA PAGINA
	}

}
