package com.pisip.jbpharmaweb.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.ParametroCalidadRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ParametroCalidadResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
import com.pisip.jbpharmaweb.service.IParametroCalidadService;

@Controller
@RequestMapping("/parametrocalidad")
public class ParametroCalidadController {

	@Autowired
	private IParametroCalidadService servicioAPI;

	@GetMapping
	public String leerpagina(Model model) {
		List<ParametroCalidadResponseDto> datosAPI = servicioAPI.listarParametros();
		List<ProductoResponseDto> listaProductos = servicioAPI.listarProductos();

		Map<Integer, String> mapaProductos = listaProductos.stream()
				.collect(Collectors.toMap(ProductoResponseDto::getIdProducto, ProductoResponseDto::getNombreProducto));
		model.addAttribute("listaparametros", datosAPI);
		model.addAttribute("mapaProductos", mapaProductos);
		return "/parametrocalidad/listaparametros";
	}

	@GetMapping("/crearparametro")
	public String leerpaginacrear(Model model) {
		model.addAttribute("parametro", new ParametroCalidadRequestDto());
		model.addAttribute("productos", servicioAPI.listarProductos());
		return "/parametrocalidad/crearparametro";
	}

	@PostMapping("/guardar")
	public String guardarParametro(@ModelAttribute ParametroCalidadRequestDto parametro,
			RedirectAttributes redirectAttributes) {

		parametro.setIdParametro(null);
		// Convertir LocalDate a java.util.Date
		Date fechaActual = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		parametro.setFechaConfiguracion(fechaActual);

		// Validar que el límite mínimo no sea mayor al máximo
		if (parametro.getLimiteMinimo() != null && parametro.getLimiteMaximo() != null) {
			if (parametro.getLimiteMinimo().compareTo(parametro.getLimiteMaximo()) > 0) {
				redirectAttributes.addFlashAttribute("error",
						"El límite mínimo no puede ser mayor que el límite máximo.");
				return "redirect:/parametrocalidad/crearparametro";
			}
		}

		try {
			servicioAPI.guardarParametro(parametro);
			redirectAttributes.addFlashAttribute("success", "Parámetro guardado correctamente.");
			return "redirect:/parametrocalidad";
		} catch (WebClientResponseException e) {
			redirectAttributes.addFlashAttribute("error", "No se pudo guardar el parámetro: " + extraerMensajeError(e));
			return "redirect:/parametrocalidad/crearparametro";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "No se pudo guardar el parámetro.");
			return "redirect:/parametrocalidad/crearparametro";
		}
	}

	@GetMapping("/editarparametro")
	public String leerpaginaeditar(@RequestParam("idParametro") int idParametro, Model model) {
		Optional<ParametroCalidadResponseDto> parametroOpt = servicioAPI.obtenerParametroPorId(idParametro);

		if (parametroOpt.isEmpty()) {
			return "redirect:/parametrocalidad?error=ParametroNoEncontrado";
		}

		ParametroCalidadResponseDto res = parametroOpt.get();

		ParametroCalidadRequestDto formDto = new ParametroCalidadRequestDto();
		formDto.setIdParametro(res.getIdParametro());
		formDto.setNombreParametro(res.getNombreParametro());
		formDto.setLimiteMaximo(res.getLimiteMaximo());
		formDto.setLimiteMinimo(res.getLimiteMinimo());
		formDto.setUnidadMedida(res.getUnidadMedida());
		formDto.setFechaConfiguracion(res.getFechaConfiguracion());
		formDto.setIdProducto(res.getIdProducto());

		model.addAttribute("parametrocalidad", formDto);
		model.addAttribute("productos", servicioAPI.listarProductos());

		return "/parametrocalidad/editarparametro";
	}

	@PostMapping("/actualizar")
	public String actualizarParametro(@ModelAttribute ParametroCalidadRequestDto parametro,
			RedirectAttributes redirectAttributes) {

		// Si no se recibe la fecha en el formulario, se asigna la fecha actual
		if (parametro.getFechaConfiguracion() == null) {
			Date fechaActual = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			parametro.setFechaConfiguracion(fechaActual);
		}

		// Validar que el límite mínimo no sea mayor al máximo
		if (parametro.getLimiteMinimo() != null && parametro.getLimiteMaximo() != null) {
			if (parametro.getLimiteMinimo().compareTo(parametro.getLimiteMaximo()) > 0) {
				redirectAttributes.addFlashAttribute("error",
						"El límite mínimo no puede ser mayor que el límite máximo.");
				return "redirect:/parametrocalidad/editarparametro?idParametro=" + parametro.getIdParametro();
			}
		}

		try {
			servicioAPI.actualizarParametro(parametro.getIdParametro(), parametro);
			redirectAttributes.addFlashAttribute("success", "Parámetro actualizado correctamente.");
			return "redirect:/parametrocalidad";
		} catch (WebClientResponseException e) {
			redirectAttributes.addFlashAttribute("error",
					"No se pudo actualizar el parámetro: " + extraerMensajeError(e));
			return "redirect:/parametrocalidad/editarparametro?idParametro=" + parametro.getIdParametro();
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "No se pudo actualizar el parámetro.");
			return "redirect:/parametrocalidad/editarparametro?idParametro=" + parametro.getIdParametro();
		}
	}

	@PostMapping("/eliminar/{idParametro}")
	public String eliminarParametro(@PathVariable int idParametro, RedirectAttributes redirectAttributes) {
		try {
			servicioAPI.eliminarParametro(idParametro);
			redirectAttributes.addFlashAttribute("success", "Parámetro eliminado correctamente.");
		} catch (WebClientResponseException e) {
			redirectAttributes.addFlashAttribute("error",
					"No se pudo eliminar el parámetro: " + extraerMensajeError(e));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el parámetro.");
		}
		return "redirect:/parametrocalidad";
	}

	private String extraerMensajeError(WebClientResponseException e) {
		String responseBody = e.getResponseBodyAsString();
		return (responseBody == null || responseBody.isBlank()) ? e.getStatusText() : responseBody;
	}
}