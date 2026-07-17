package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharma.dominio.entidades.EnsayoVariable;

@Controller
@RequestMapping("/variables") // URL
public class EnsayoVariableController {

	@GetMapping
	public String leerpagina() {
		return "/ensayovariable/variables"; // UBICACIÓN FÍSICA DE LA PÁGINA
	}

	@GetMapping("/nueva")
	public String crearpagina(Model model) {
		model.addAttribute("variable", new EnsayoVariable());
		return "/ensayovariable/crearvariable";
	}

	@GetMapping("/{id}/editar")
	public String editarpagina(@PathVariable Long id, Model model) {
		EnsayoVariable variable = new EnsayoVariable();
		variable.setIdVariable(id);
		model.addAttribute("variable", variable);
		return "/ensayovariable/editarvariable";
	}
}
