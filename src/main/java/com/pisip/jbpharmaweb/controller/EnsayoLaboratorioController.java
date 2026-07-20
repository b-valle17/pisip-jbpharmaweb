package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.EnsayoLaboratorioRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;
import com.pisip.jbpharmaweb.service.iEnsayoLaboratorioService;

@Controller
@RequestMapping("/ensayos")
public class EnsayoLaboratorioController {

	private final iEnsayoLaboratorioService servicio;
	private final WebClient webClient;

	public EnsayoLaboratorioController(
			iEnsayoLaboratorioService servicio,
			WebClient webClient) {
		this.servicio = servicio;
		this.webClient = webClient;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("ensayos", servicio.listar());
		return "ensayo/listaensayo";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("ensayo", new EnsayoLaboratorioRequestDto());
		cargarRelaciones(model);
		return "ensayo/crearensayo";
	}

	@PostMapping("/guardar")
	public String guardar(
			@ModelAttribute("ensayo") EnsayoLaboratorioRequestDto dto,
			RedirectAttributes ra) {

		// El ID y el código se generan automáticamente en la API.
		dto.setIdEnsayo(null);
		dto.setCodigoEnsayo(null);

		servicio.guardar(dto);
		ra.addFlashAttribute("success", "Registro guardado correctamente.");
		return "redirect:/ensayos";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable long id, Model model) {
		EnsayoLaboratorioResponseDto r = servicio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Registro no encontrado"));

		EnsayoLaboratorioRequestDto d = new EnsayoLaboratorioRequestDto();
		copiar(r, d);

		model.addAttribute("ensayo", d);
		cargarRelaciones(model);
		return "ensayo/editarensayo";
	}

	@PostMapping("/{id}/actualizar")
	public String actualizar(
			@PathVariable long id,
			@ModelAttribute("ensayo") EnsayoLaboratorioRequestDto dto,
			RedirectAttributes ra) {

		servicio.actualizar(id, dto);
		ra.addFlashAttribute("success", "Registro actualizado correctamente.");
		return "redirect:/ensayos";
	}

	@PostMapping("/{id}/eliminar")
	public String eliminar(@PathVariable long id, RedirectAttributes ra) {
		servicio.eliminar(id);
		ra.addFlashAttribute("success", "Registro eliminado correctamente.");
		return "redirect:/ensayos";
	}

	private void cargarRelaciones(Model model) {
		model.addAttribute("ordenes", listar("/api/ordenProduccion"));
		model.addAttribute("productos", listar("/api/productos"));
	}

	private List<Map<String, Object>> listar(String ruta) {
		return webClient.get()
				.uri(ruta)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
				.blockOptional()
				.orElseGet(List::of);
	}

	private void copiar(EnsayoLaboratorioResponseDto r, EnsayoLaboratorioRequestDto d) {
		d.setIdEnsayo(r.getIdEnsayo());
		d.setIdOrden(r.getIdOrden());
		d.setIdProducto(r.getIdProducto());
		d.setCodigoEnsayo(r.getCodigoEnsayo());
		d.setFechaEnsayo(r.getFechaEnsayo());
		d.setResponsable(r.getResponsable());
		d.setObservacion(r.getObservacion());
		d.setEstado(r.getEstado());
		d.setCreadoEn(r.getCreadoEn());
	}
}
