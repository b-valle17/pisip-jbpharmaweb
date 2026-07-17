package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crearensayo") // URL
public class PruebasController {
	public String leerpagina() {
		return "/laboratorioensayo/crearensayo"; // UBICACIÓN FÍSICA DE LA PÁGINA
	}
}
