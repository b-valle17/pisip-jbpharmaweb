package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parametros") //URL
public class ParametroValidacion {
	@GetMapping
	public String leerpagina() {
		return "/parametrovalidacion/parametros"; //UBICACIÓN FíSICA DE LA PAGINA
	}
}
