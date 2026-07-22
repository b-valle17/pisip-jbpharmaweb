package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.DictamenLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;
import com.pisip.jbpharmaweb.service.IDictamenLoteService;

@Controller
@RequestMapping("/dictamenLote")
public class DictamenLoteController {

	@Autowired
	private IDictamenLoteService servicioAPI;

	@GetMapping
	public String leerpagina(Model model) {
		List<DictamenLoteResponseDto> datosAPI = servicioAPI.listarDictamenLote();
		model.addAttribute("listadictamenlote", datosAPI);
		return "/dictamenLote/listarDictamenLote";
	}

	@GetMapping("/crearDictamenLote")
	public String leerpaginacrear(Model model) {
		model.addAttribute("dictamenLote", new DictamenLoteRequestDto());
		return "/dictamenLote/crearDictamenLote";
	}

	@PostMapping("/guardar")
	public String guardarDictamenLote(@ModelAttribute DictamenLoteRequestDto dictamenLote) {
		servicioAPI.guardarDictamenLote(dictamenLote);
		return "redirect:/dictamenLote";
	}

	@GetMapping("/editarDictamenLote")
	public String leerpaginaeditar() {
		return "/dictamenLote/editarDictamenLote";
	}

}
