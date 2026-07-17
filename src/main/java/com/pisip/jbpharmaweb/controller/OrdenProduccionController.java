package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.OrdenProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;
import com.pisip.jbpharmaweb.service.IOrdenProduccionService;

@Controller
@RequestMapping("/ordenproduccion")
public class OrdenProduccionController {
	@Autowired
	private IOrdenProduccionService servicioAPI;
	
	@GetMapping
	public String leerpagina(Model model) {
		List<OrdenProduccionResponseDto> datosAPI = servicioAPI.listarOrden();
		model.addAttribute("listaorden", datosAPI);
		return "/ordenproduccion/listarorden"; 
	}
	@GetMapping("/crearorden")
	public String leerpaginacrear(Model model) {
		model.addAttribute("orden", new OrdenProduccionRequestDto());
		return "/ordenproduccion/crearorden";
	}
	@PostMapping("/guardar")
	public String guardarOrden(@ModelAttribute OrdenProduccionRequestDto orden) {
		servicioAPI.guardarOrden(orden);
		return "redirect:/ordenproduccion";
	}
	@GetMapping("/editarorden")
	public String leerpaginaeditar() {
		return "/ordenproduccion/editarorden";
	}
}
