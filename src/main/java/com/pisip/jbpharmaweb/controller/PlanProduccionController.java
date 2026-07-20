package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.PlanProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.PlanProduccionResponseDto;
import com.pisip.jbpharmaweb.service.IPlanProduccionService;

@Controller
@RequestMapping("/planproduccion")
public class PlanProduccionController {
	@Autowired
	private IPlanProduccionService servicioAPI;
	
	@GetMapping
	public String leerpagina(Model model) {
		List<PlanProduccionResponseDto> datosAPI = servicioAPI.listarPlan();
		model.addAttribute("listaplan", datosAPI);
		return "/planproduccion/listarplan"; 
	}
	@GetMapping("/crearplan")
	public String leerpaginacrear(Model model) {
		model.addAttribute("plan", new PlanProduccionRequestDto());
		return "/planproduccion/crearplan";
	}
	@PostMapping("/guardar")
	public String guardarPlan(@ModelAttribute PlanProduccionRequestDto plan) {
		servicioAPI.guardarPlan(plan);
		return "redirect:/planproduccion";
	}
	@GetMapping("/editarplan")
	public String leerpaginaeditar() {
		return "/planproduccion/editarplan";
	}
}
