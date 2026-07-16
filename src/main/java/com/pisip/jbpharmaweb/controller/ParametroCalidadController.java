package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.ParametroCalidadRequestDto;
import com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO;
import com.pisip.jbpharmaweb.model.dto.response.ParametroCalidadResponseDto;
import com.pisip.jbpharmaweb.service.IParametroCalidadService;

@Controller
@RequestMapping("/parametrocalidad")
public class ParametroCalidadController {
	
	@Autowired
	private IParametroCalidadService servicioAPI;
	@GetMapping
	public String leerpagina(Model model) {
		List<ParametroCalidadResponseDto> datosAPI = servicioAPI.listarParametros();
		model.addAttribute("listaparametros", datosAPI);
		return "/parametrocalidad/listaparametros";
	}
	
	@GetMapping("/crearparametro")
	public String leerpaginacrear(Model model) {
		model.addAttribute("parametro", new ParametroCalidadRequestDto());
		model.addAttribute("productos", servicioAPI.listarProductos());
		return "/parametrocalidad/crearparametro";
	}
	
	@PostMapping("/guardar")
	public String guardarParametro(@ModelAttribute ParametroCalidadRequestDto parametro) {
		servicioAPI.guardarParametro(parametro);
		return "redirect:/parametrocalidad";
	}
	
	@GetMapping("/editarparametro")
	public String leerpaginaeditar() {
		return "/parametrocalidad/editarparametro";
	}

}
