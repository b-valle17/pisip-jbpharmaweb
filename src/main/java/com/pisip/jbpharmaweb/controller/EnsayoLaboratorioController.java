package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ensayos") //URL
public class EnsayoLaboratorioController {
	
	@GetMapping
	public String leerpagina() {
		return "/ensayolaboratorio/ensayos"; //UBICACIÓN FíSICA DE LA PAGINA
	}

}
