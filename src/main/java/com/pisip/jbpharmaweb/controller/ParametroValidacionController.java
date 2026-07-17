package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharma.dominio.entidades.ParametroValidacion;

@Controller
@RequestMapping("/parametrosvalidacion") // URL
public class ParametroValidacionController {

	@GetMapping
	public String leerpagina() {
		return "/parametrovalidacion/parametrosvalidacion"; // UBICACIÓN FÍSICA DE LA PÁGINA
	}

	@GetMapping("/nuevo")
	public String crearpagina(Model model) {
		model.addAttribute("parametro", new ParametroValidacion());
		return "/parametrovalidacion/crearparametrovalidacion";
	}

	@GetMapping("/{id}/editar")
	public String editarpagina(@PathVariable Long id, Model model) {
		ParametroValidacion parametro = new ParametroValidacion();
		parametro.setIdParametro(id);
		model.addAttribute("parametro", parametro);
		return "/parametrovalidacion/editarparametrovalidacion";
	}
}
