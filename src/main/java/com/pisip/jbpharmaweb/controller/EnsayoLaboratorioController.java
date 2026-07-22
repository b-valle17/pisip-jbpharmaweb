package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.EnsayoLaboratorioRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
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
		List<OrdenProduccionResponseDto> ordenes = listarOrdenes();
		List<ProductoResponseDto> productos = listarProductos();
		model.addAttribute("ordenesPorId", ordenes.stream()
				.collect(Collectors.toMap(OrdenProduccionResponseDto::getIdOrden, Function.identity(), (a, b) -> a)));
		model.addAttribute("productosPorId", productos.stream()
				.collect(Collectors.toMap(ProductoResponseDto::getIdProducto, Function.identity(), (a, b) -> a)));
		return "ensayo/listaensayo";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
        if (!model.containsAttribute("ensayoError")) {
            model.addAttribute("ensayo", new EnsayoLaboratorioRequestDto());
        } else {
            model.addAttribute("ensayo", model.getAttribute("ensayoError"));
        }
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

        try {
            servicio.guardar(dto);
            ra.addFlashAttribute("success", "Registro guardado correctamente.");
            return "redirect:/ensayos";
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "No se pudo guardar el ensayo: " + extraerMensaje(ex));
            ra.addFlashAttribute("ensayoError", dto);
            return "redirect:/ensayos/nuevo";
        }
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

        try {
            servicio.actualizar(id, dto);
            ra.addFlashAttribute("success", "Registro actualizado correctamente.");
            return "redirect:/ensayos";
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "No se pudo actualizar el ensayo: " + extraerMensaje(ex));
            return "redirect:/ensayos/" + id + "/editar";
        }
	}

	@PostMapping("/{id}/eliminar")
	public String eliminar(@PathVariable long id, RedirectAttributes ra) {
		servicio.eliminar(id);
		ra.addFlashAttribute("success", "Registro eliminado correctamente.");
		return "redirect:/ensayos";
	}

	private void cargarRelaciones(Model model) {
		model.addAttribute("ordenes", listarOrdenes());
		model.addAttribute("productos", listarProductos());
	}

	private List<OrdenProduccionResponseDto> listarOrdenes() {
		return webClient.get().uri("/ordenProduccion").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<OrdenProduccionResponseDto>>() {})
				.blockOptional().orElseGet(List::of);
	}

	private List<ProductoResponseDto> listarProductos() {
		return webClient.get().uri("/productos").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<ProductoResponseDto>>() {})
				.blockOptional().orElseGet(List::of);
	}

    private String extraerMensaje(Exception ex) {
        Throwable causa = ex;
        while (causa != null) {
            if (causa instanceof WebClientResponseException webEx) {
                String cuerpo = webEx.getResponseBodyAsString();
                if (cuerpo != null && !cuerpo.isBlank()) {
                    int inicio = cuerpo.indexOf("\"message\"");
                    if (inicio >= 0) {
                        int dosPuntos = cuerpo.indexOf(':', inicio);
                        int primeraComilla = cuerpo.indexOf('\"', dosPuntos + 1);
                        int segundaComilla = cuerpo.indexOf('\"', primeraComilla + 1);
                        if (primeraComilla >= 0 && segundaComilla > primeraComilla) {
                            return cuerpo.substring(primeraComilla + 1, segundaComilla);
                        }
                    }
                    return cuerpo;
                }
                return webEx.getStatusText();
            }
            if (causa.getCause() == null) break;
            causa = causa.getCause();
        }
        String mensaje = causa != null ? causa.getMessage() : ex.getMessage();
        return mensaje == null || mensaje.isBlank() ? ex.getClass().getSimpleName() : mensaje;
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
