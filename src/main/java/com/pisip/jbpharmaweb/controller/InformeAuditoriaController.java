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

import com.pisip.jbpharmaweb.model.dto.request.InformeAuditoriaRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.InformeAuditoriaResponseDto;
import com.pisip.jbpharmaweb.service.IAuditoriaLoteService;
import com.pisip.jbpharmaweb.service.IInformeAuditoriaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/informeAuditoria")
public class InformeAuditoriaController {

	private static final String ROL_SUPERVISOR = "SUPERVISOR";

	@Autowired
	private IInformeAuditoriaService servicioAPI;

	@Autowired
	private IAuditoriaLoteService auditoriaLoteService;

	@GetMapping
	public String leerpagina(Model model) {
		List<InformeAuditoriaResponseDto> datosAPI = servicioAPI.listarInformeAuditoria();
		model.addAttribute("listainformeauditoria", datosAPI);
		return "/informeAuditoria/listarInformeAuditoria";
	}

	@GetMapping("/crearInformeAuditoria")
	public String leerpaginacrear(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		if (!esSupervisor(session)) {
			redirectAttributes.addFlashAttribute("error", "Solo el Supervisor puede generar informes de auditoría.");
			return "redirect:/informeAuditoria";
		}
		model.addAttribute("informeAuditoria", new InformeAuditoriaRequestDto());
		model.addAttribute("auditorias", auditoriaLoteService.listarAuditoriaLote());
		return "/informeAuditoria/crearInformeAuditoria";
	}

	@PostMapping("/guardar")
	public String guardarInformeAuditoria(@ModelAttribute InformeAuditoriaRequestDto informeAuditoria,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (!esSupervisor(session)) {
			redirectAttributes.addFlashAttribute("error", "Solo el Supervisor puede generar informes de auditoría.");
			return "redirect:/informeAuditoria";
		}
		servicioAPI.guardarInformeAuditoria(informeAuditoria);
		redirectAttributes.addFlashAttribute("success", "Informe de auditoría generado correctamente.");
		return "redirect:/informeAuditoria";
	}

	@PostMapping("/{idInforme}/comentar")
	public String comentarInformeAuditoria(@PathVariable int idInforme, @RequestParam String comentario,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (!esSupervisor(session)) {
			redirectAttributes.addFlashAttribute("error", "Solo el Supervisor puede comentar un informe de auditoría.");
			return "redirect:/informeAuditoria";
		}
		servicioAPI.comentarInformeAuditoria(idInforme, comentario);
		redirectAttributes.addFlashAttribute("success", "Comentario registrado correctamente.");
		return "redirect:/informeAuditoria";
	}

	private boolean esSupervisor(HttpSession session) {
		Object rol = session.getAttribute("rolUsuario");
		return rol != null && ROL_SUPERVISOR.equalsIgnoreCase(rol.toString());
	}

}
