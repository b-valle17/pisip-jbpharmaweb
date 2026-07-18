package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.AuditoriaLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.AuditoriaLoteResponseDto;
import com.pisip.jbpharmaweb.service.IAuditoriaLoteService;

@Controller
@RequestMapping("/auditoriaLote")
public class AuditoriaLoteController {

	@Autowired
	private IAuditoriaLoteService servicioAPI;

	@GetMapping
	public String leerpagina(Model model) {
		List<AuditoriaLoteResponseDto> datosAPI = servicioAPI.listarAuditoriaLote();
		model.addAttribute("listaauditorialote", datosAPI);
		return "/auditoriaLote/listarAuditoriaLote";
	}

	@GetMapping("/crearAuditoriaLote")
	public String leerpaginacrear(Model model) {
		model.addAttribute("auditoriaLote", new AuditoriaLoteRequestDto());
		return "/auditoriaLote/crearAuditoriaLote";
	}

	@PostMapping("/guardar")
	public String guardarAuditoriaLote(@ModelAttribute AuditoriaLoteRequestDto auditoriaLote) {
		servicioAPI.guardarAuditoriaLote(auditoriaLote);
		return "redirect:/auditoriaLote";
	}

	@GetMapping("/editarAuditoriaLote")
	public String leerpaginaeditar() {
		return "/auditoriaLote/editarAuditoriaLote";
	}

}
