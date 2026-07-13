package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/informeAuditoria")
public class InformeAuditoriaController {

	@GetMapping
	public String leerpagina() {
		return "/informeAuditoria/listarInformeAuditoria";
	}

	@GetMapping("/crearInformeAuditoria")
	public String leerpaginacrear() {
		return "/informeAuditoria/crearInformeAuditoria";
	}

	@GetMapping("/editarInformeAuditoria")
	public String leerpaginaeditar() {
		return "/informeAuditoria/editarInformeAuditoria";
	}

}
