package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validaciones") //URL
public class ValidacionSemaforica {
	
	@GetMapping
	public String leerpagina() {
		return "/validacionsemaforica/validaciones"; //UBICACIÓN FíSICA DE LA PAGINA
	}

}
