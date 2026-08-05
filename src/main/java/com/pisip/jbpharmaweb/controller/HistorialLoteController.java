package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;
import com.pisip.jbpharmaweb.service.IHistorialLoteService;
import com.pisip.jbpharmaweb.service.IOrdenProduccionService;

@Controller
@RequestMapping("/historialLote")
public class HistorialLoteController {

	@Autowired
	private IHistorialLoteService servicioAPI;

	@Autowired
	private IOrdenProduccionService ordenProduccionService; // Service inyectado

	@GetMapping
	public String leerpagina(@RequestParam(required = false) String estado, Model model) {
		List<HistorialLoteResponseDto> datosAPI = servicioAPI.listarFinalizados(estado);
		
		// 1. Obtener lista de órdenes de producción
		List<OrdenProduccionResponseDto> listaOrdenes = ordenProduccionService.listarOrden();

		// 2. Crear mapa (idOrden -> numeroLote)
		Map<Integer, String> mapaOrdenes = listaOrdenes.stream()
				.filter(o -> o.getIdOrden() != null && o.getNumeroLote() != null)
				.collect(Collectors.toMap(
						OrdenProduccionResponseDto::getIdOrden, 
						OrdenProduccionResponseDto::getNumeroLote, 
						(e, r) -> e
				));

		model.addAttribute("listahistoriallote", datosAPI);
		model.addAttribute("mapaOrdenes", mapaOrdenes); // Pasar el mapa a Thymeleaf
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