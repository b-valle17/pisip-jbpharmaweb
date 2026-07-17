package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharma.dominio.entidades.EnsayoLaboratorio;

@Controller
@RequestMapping("/ensayos") // URL
public class EnsayoLaboratorioController {

	@GetMapping
	public String leerpagina() {
		return "/ensayolaboratorio/ensayos"; // UBICACIÓN FÍSICA DE LA PÁGINA
	}

	@GetMapping("/nuevo")
	public String crearpagina(Model model) {
	    model.addAttribute( "ensayo", new EnsayoLaboratorio());
	    return "ensayolaboratorio/crearensayo";
	}

	@GetMapping("/{id}")
	public String detallepagina(@PathVariable Long id, Model model) {
		EnsayoLaboratorio ensayo = new EnsayoLaboratorio();
		ensayo.setIdEnsayo(id);
		model.addAttribute("ensayo", ensayo);
		return "/ensayolaboratorio/detalleensayo";
	}

	@GetMapping("/{id}/editar")
	public String editarpagina(@PathVariable Long id, Model model) {
		EnsayoLaboratorio ensayo = new EnsayoLaboratorio();
		ensayo.setIdEnsayo(id);
		model.addAttribute("ensayo", ensayo);
		return "/ensayolaboratorio/editarensayo";
	}
}
