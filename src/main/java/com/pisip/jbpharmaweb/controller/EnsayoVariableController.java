package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.EnsayoVariableRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoVariableResponseDto;
import com.pisip.jbpharmaweb.service.iEnsayoLaboratorioService;
import com.pisip.jbpharmaweb.service.iEnsayoVariableService;

@Controller
@RequestMapping("/variables")
public class EnsayoVariableController {

	private final iEnsayoVariableService servicio;
	private final iEnsayoLaboratorioService ensayoServicio;

	public EnsayoVariableController(
			iEnsayoVariableService servicio,
			iEnsayoLaboratorioService ensayoServicio) {
		this.servicio = servicio;
		this.ensayoServicio = ensayoServicio;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("variables", servicio.listar());
		return "variable/listavariable";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("variable", new EnsayoVariableRequestDto());
		model.addAttribute("ensayos", ensayoServicio.listar());
		return "variable/crearvariable";
	}

	@PostMapping("/guardar")
	public String guardar(
			@ModelAttribute("variable") EnsayoVariableRequestDto dto,
			RedirectAttributes ra) {

		dto.setIdVariable(null);
		servicio.guardar(dto);

		ra.addFlashAttribute("success", "Registro guardado correctamente.");
		return "redirect:/variables";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable long id, Model model) {
		EnsayoVariableResponseDto r = servicio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Registro no encontrado"));

		EnsayoVariableRequestDto d = new EnsayoVariableRequestDto();
		copiar(r, d);

		model.addAttribute("variable", d);
		model.addAttribute("ensayos", ensayoServicio.listar());
		return "variable/editarvariable";
	}

	@PostMapping("/{id}/actualizar")
	public String actualizar(
			@PathVariable long id,
			@ModelAttribute("variable") EnsayoVariableRequestDto dto,
			RedirectAttributes ra) {

		servicio.actualizar(id, dto);
		ra.addFlashAttribute("success", "Registro actualizado correctamente.");
		return "redirect:/variables";
	}

	@PostMapping("/{id}/eliminar")
	public String eliminar(@PathVariable long id, RedirectAttributes ra) {
		servicio.eliminar(id);
		ra.addFlashAttribute("success", "Registro eliminado correctamente.");
		return "redirect:/variables";
	}

	private void copiar(EnsayoVariableResponseDto r, EnsayoVariableRequestDto d) {
		d.setIdVariable(r.getIdVariable());
		d.setIdEnsayo(r.getIdEnsayo());
		d.setNombreVariable(r.getNombreVariable());
		d.setValorObtenido(r.getValorObtenido());
		d.setUnidadMedida(r.getUnidadMedida());
		d.setCreadoEn(r.getCreadoEn());
	}
}
