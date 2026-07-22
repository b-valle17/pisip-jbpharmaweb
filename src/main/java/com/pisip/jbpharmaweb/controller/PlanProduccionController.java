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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/editarplan")
	public String mostrarFormularioEditar(@RequestParam("idPlan") Integer idPlan, Model model) {

	    PlanProduccionResponseDto plan = servicioAPI.buscarPorId(idPlan);

	    PlanProduccionRequestDto dto = new PlanProduccionRequestDto();
	    dto.setIdPlan(plan.getIdPlan());
	    dto.setCodigoPlan(plan.getCodigoPlan());
	    dto.setAnio(plan.getAnio());
	    dto.setMes(plan.getMes());
	    dto.setFechaEmision(plan.getFechaEmision());
	    dto.setEstado(plan.getEstado());
	    dto.setDescripcion(plan.getDescripcion());
	    
	    if (plan.getIdUsuario() != null) {
	        dto.setIdUsuario(plan.getIdUsuario());
	    }
	    model.addAttribute("plan", dto);
	    model.addAttribute("listausuarios", usuarioService.listarUsuarios());
	    
	    return "/planproduccion/editarplan";
	}

	@PostMapping("/actualizar")
	public String actualizarPlan(@ModelAttribute("plan") PlanProduccionRequestDto dto,
	                             RedirectAttributes redirectAttributes) {
	    try {
	        servicioAPI.actualizarPlan(dto.getIdPlan(), dto);
	        redirectAttributes.addFlashAttribute("success", "Plan de producción actualizado correctamente.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al actualizar el plan de producción.");
	    }
	    return "redirect:/planproduccion";
	}
}
