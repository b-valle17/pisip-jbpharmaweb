package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.InformeAuditoriaRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.InformeAuditoriaResponseDto;
import com.pisip.jbpharmaweb.service.IInformeAuditoriaService;

@Controller
@RequestMapping("/informeAuditoria")
public class InformeAuditoriaController {

	@Autowired
	private IInformeAuditoriaService servicioAPI;

	@GetMapping
	public String leerpagina(Model model) {
		List<InformeAuditoriaResponseDto> datosAPI = servicioAPI.listarInformeAuditoria();
		model.addAttribute("listainformeauditoria", datosAPI);
		return "/informeAuditoria/listarInformeAuditoria";
	}

	@GetMapping("/crearInformeAuditoria")
	public String leerpaginacrear(Model model) {
		model.addAttribute("informeAuditoria", new InformeAuditoriaRequestDto());
		return "/informeAuditoria/crearInformeAuditoria";
	}

	@PostMapping("/guardar")
	public String guardarInformeAuditoria(@ModelAttribute InformeAuditoriaRequestDto informeAuditoria) {
		servicioAPI.guardarInformeAuditoria(informeAuditoria);
		return "redirect:/informeAuditoria";
	}

	@GetMapping("/editarInformeAuditoria")
	public String leerpaginaeditar() {
		return "/informeAuditoria/editarInformeAuditoria";
	}

}
