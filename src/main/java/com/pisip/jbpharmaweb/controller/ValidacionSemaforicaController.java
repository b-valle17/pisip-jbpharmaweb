package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharma.dominio.entidades.ValidacionSemaforica;

@Controller
@RequestMapping("/validaciones") // URL
public class ValidacionSemaforicaController {

	@GetMapping
	public String leerpagina() {
		return "/validacionsemaforica/validaciones"; // UBICACIÓN FÍSICA DE LA PÁGINA
	}

	@GetMapping("/{id}")
	public String detallepagina(@PathVariable Long id, Model model) {
		ValidacionSemaforica validacion = new ValidacionSemaforica();
		validacion.setIdValidacion(id);
		model.addAttribute("validacion", validacion);
		return "/validacionsemaforica/detallevalidacion";
	}
}
