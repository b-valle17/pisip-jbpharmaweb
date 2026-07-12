package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/variables") //URL
public class EnsayoVariableController {
	
	@GetMapping
	public String leerpagina() {
		return "/ensayovariable/variables"; //UBICACIÓN FíSICA DE LA PAGINA
	}

}
