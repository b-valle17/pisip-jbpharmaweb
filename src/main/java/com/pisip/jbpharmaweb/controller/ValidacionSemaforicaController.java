package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.ValidacionSemaforicaRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ValidacionSemaforicaResponseDto;
import com.pisip.jbpharmaweb.service.iEnsayoVariableService;
import com.pisip.jbpharmaweb.service.iValidacionSemaforicaService;

@Controller
@RequestMapping("/validaciones")
public class ValidacionSemaforicaController {

	private final iValidacionSemaforicaService servicio;
	private final iEnsayoVariableService variableServicio;
	private final WebClient webClient;

	public ValidacionSemaforicaController(
			iValidacionSemaforicaService servicio,
			iEnsayoVariableService variableServicio,
			WebClient webClient) {
		this.servicio = servicio;
		this.variableServicio = variableServicio;
		this.webClient = webClient;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("validaciones", servicio.listar());
		return "validacionsemaforica/validaciones";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("validacion", new ValidacionSemaforicaRequestDto());
		cargarRelaciones(model);
		return "validacionsemaforica/detallevalidacion";
	}

	@PostMapping("/guardar")
	public String guardar(
			@ModelAttribute("validacion") ValidacionSemaforicaRequestDto dto,
			RedirectAttributes ra) {

		dto.setIdValidacion(null);
		servicio.guardar(dto);

		ra.addFlashAttribute("success", "Registro guardado correctamente.");
		return "redirect:/validaciones";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable long id, Model model) {
		ValidacionSemaforicaResponseDto r = servicio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Registro no encontrado"));

		ValidacionSemaforicaRequestDto d = new ValidacionSemaforicaRequestDto();
		copiar(r, d);

		model.addAttribute("validacion", d);
		cargarRelaciones(model);
		return "validacionsemaforica/detallevalidacion";
	}

	@PostMapping("/{id}/actualizar")
	public String actualizar(
			@PathVariable long id,
			@ModelAttribute("validacion") ValidacionSemaforicaRequestDto dto,
			RedirectAttributes ra) {

		servicio.actualizar(id, dto);
		ra.addFlashAttribute("success", "Registro actualizado correctamente.");
		return "redirect:/validaciones";
	}

	@PostMapping("/{id}/eliminar")
	public String eliminar(@PathVariable long id, RedirectAttributes ra) {
		servicio.eliminar(id);
		ra.addFlashAttribute("success", "Registro eliminado correctamente.");
		return "redirect:/validaciones";
	}

	private void cargarRelaciones(Model model) {
		model.addAttribute("variables", variableServicio.listar());
		model.addAttribute("parametros", listarParametros());
	}

	private List<Map<String, Object>> listarParametros() {
		return webClient.get()
				.uri("/api/parametros-calidad")
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
				.blockOptional()
				.orElseGet(List::of);
	}

	private void copiar(ValidacionSemaforicaResponseDto r, ValidacionSemaforicaRequestDto d) {
		d.setIdValidacion(r.getIdValidacion());
		d.setIdVariable(r.getIdVariable());
		d.setIdParametro(r.getIdParametro());
		d.setResultado(r.getResultado());
		d.setMensaje(r.getMensaje());
		d.setFechaValidacion(r.getFechaValidacion());
	}
}
