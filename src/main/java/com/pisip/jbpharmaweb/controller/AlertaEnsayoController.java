package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.AlertaEnsayoRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.AlertaEnsayoResponseDto;
import com.pisip.jbpharmaweb.service.iAlertaEnsayoService;
import com.pisip.jbpharmaweb.service.iValidacionSemaforicaService;

@Controller
@RequestMapping("/alertas")
public class AlertaEnsayoController {

	private final iAlertaEnsayoService servicio;
	private final iValidacionSemaforicaService validacionServicio;

	public AlertaEnsayoController(
			iAlertaEnsayoService servicio,
			iValidacionSemaforicaService validacionServicio) {
		this.servicio = servicio;
		this.validacionServicio = validacionServicio;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("alertas", servicio.listar());
		return "alerta/listaalerta";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("alerta", new AlertaEnsayoRequestDto());
		model.addAttribute("validaciones", validacionServicio.listar());
		return "alertas/detallealerta";
	}

	@PostMapping("/guardar")
	public String guardar(
			@ModelAttribute("alerta") AlertaEnsayoRequestDto dto,
			RedirectAttributes ra) {

		dto.setIdAlerta(null);
		servicio.guardar(dto);

		ra.addFlashAttribute("success", "Registro guardado correctamente.");
		return "redirect:/alertas";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable long id, Model model) {
		AlertaEnsayoResponseDto r = servicio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Registro no encontrado"));

		AlertaEnsayoRequestDto d = new AlertaEnsayoRequestDto();
		copiar(r, d);

		model.addAttribute("alerta", d);
		model.addAttribute("validaciones", validacionServicio.listar());
		return "alerta/detallealerta";
	}

	@PostMapping("/{id}/actualizar")
	public String actualizar(
			@PathVariable long id,
			@ModelAttribute("alerta") AlertaEnsayoRequestDto dto,
			RedirectAttributes ra) {

		servicio.actualizar(id, dto);
		ra.addFlashAttribute("success", "Registro actualizado correctamente.");
		return "redirect:/alertas";
	}

	@PostMapping("/{id}/eliminar")
	public String eliminar(@PathVariable long id, RedirectAttributes ra) {
		servicio.eliminar(id);
		ra.addFlashAttribute("success", "Registro eliminado correctamente.");
		return "redirect:/alertas";
	}

	private void copiar(AlertaEnsayoResponseDto r, AlertaEnsayoRequestDto d) {
		d.setIdAlerta(r.getIdAlerta());
		d.setIdValidacion(r.getIdValidacion());
		d.setTipoAlerta(r.getTipoAlerta());
		d.setDestinatario(r.getDestinatario());
		d.setAsunto(r.getAsunto());
		d.setMensaje(r.getMensaje());
		d.setEstadoEnvio(r.getEstadoEnvio());
		d.setFechaGeneracion(r.getFechaGeneracion());
		d.setFechaEnvio(r.getFechaEnvio());
	}
}
