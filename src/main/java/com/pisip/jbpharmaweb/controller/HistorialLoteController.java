package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pisip.jbpharmaweb.model.dto.request.HistorialLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.HistorialLoteResponseDto;
import com.pisip.jbpharmaweb.service.IHistorialLoteService;

@Controller
@RequestMapping("/historialLote")
public class HistorialLoteController {

	@Autowired
	private IHistorialLoteService servicioAPI;

	// Muestra solo los lotes ya aceptados/rechazados (con su estado), como pide el requerimiento.
	// Filtro opcional: /historialLote?estado=ACEPTADO  o  /historialLote?estado=RECHAZADO
	@GetMapping
	public String leerpagina(@RequestParam(required = false) String estado, Model model) {
		List<HistorialLoteResponseDto> datosAPI = servicioAPI.listarFinalizados(estado);
		model.addAttribute("listahistoriallote", datosAPI);
		model.addAttribute("estadoSeleccionado", estado);
		return "/historialLote/listarHistorialLote";
	}

	@GetMapping("/crearHistorialLote")
	public String leerpaginacrear(Model model) {
		model.addAttribute("historialLote", new HistorialLoteRequestDto());
		return "/historialLote/crearHistorialLote";
	}

	@PostMapping("/guardar")
	public String guardarHistorialLote(@ModelAttribute HistorialLoteRequestDto historialLote) {
		servicioAPI.guardarHistorialLote(historialLote);
		return "redirect:/historialLote";
	}

	@GetMapping("/editarHistorialLote")
	public String leerpaginaeditar() {
		return "/historialLote/editarHistorialLote";
	}

}