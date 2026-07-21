package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.PlanProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.PlanProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IPlanProduccionService;
import com.pisip.jbpharmaweb.service.IUsuarioService;

@Controller
@RequestMapping("/planproduccion")
public class PlanProduccionController {
	@Autowired
	private IPlanProduccionService servicioAPI;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping
	public String leerpagina(Model model) {
		List<PlanProduccionResponseDto> datosAPI = servicioAPI.listarPlan();
		model.addAttribute("listaplan", datosAPI);
		return "/planproduccion/listarplan"; 
	}
	@GetMapping("/crearplan")
	public String leerpaginacrear(Model model) {
		model.addAttribute("plan", new PlanProduccionRequestDto());
		List<UsuarioResponseDTO> listaUsuarios = usuarioService.listarUsuarios(); 
		model.addAttribute("listausuarios", listaUsuarios); 
		
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
	
	@PostMapping("/eliminar/{id}")
	public String eliminarPlan(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
	    try {
	        servicioAPI.eliminarPlan(id);
	        redirectAttributes.addFlashAttribute("success", "Plan de producción eliminado correctamente.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el plan de producción.");
	    }
	    return "redirect:/planproduccion";
	}
}
