package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.Map;
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

import com.pisip.jbpharmaweb.model.dto.request.DictamenLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IDictamenLoteService;
// 🔹 Importa los servicios y DTOs de Órdenes y Usuarios según el paquete de tu proyecto
import com.pisip.jbpharmaweb.service.IOrdenProduccionService;
import com.pisip.jbpharmaweb.service.IUsuarioService;

@Controller
@RequestMapping("/dictamenLote")
public class DictamenLoteController {

	@Autowired
	private IDictamenLoteService servicioAPI;

	// 🔹 Inyectamos los servicios para obtener las listas desplegables
	@Autowired
	private IOrdenProduccionService ordenProduccionService;

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping
	public String leerpagina(Model model) {
		List<DictamenLoteResponseDto> datosAPI = servicioAPI.listarDictamenLote();
		List<UsuarioResponseDTO> listaUsuarios = usuarioService.listarUsuarios();
		List<OrdenProduccionResponseDto> listaOrden = ordenProduccionService.listarOrden();

		// Convertimos la lista de roles en un mapa [ID: Nombre] para buscar fácil en
		// Thymeleaf
		Map<Integer, String> mapaUsuarios = listaUsuarios.stream()
				.collect(Collectors.toMap(UsuarioResponseDTO::getIdUsuario, UsuarioResponseDTO::getNombre));
		Map<Integer, String> mapaOrdenes = listaOrden.stream().collect(
				Collectors.toMap(OrdenProduccionResponseDto::getIdOrden, OrdenProduccionResponseDto::getNumeroLote));
		model.addAttribute("listadictamenlote", datosAPI);
		model.addAttribute("mapaUsuarios", mapaUsuarios); // Pasamos el mapa a la vista
		model.addAttribute("mapaOrdenes", mapaOrdenes); // Pasamos el mapa a la vista
		return "dictamenLote/listarDictamenLote";
	}

	@GetMapping("/crearDictamenLote")
	public String leerpaginacrear(Model model) {
		model.addAttribute("dictamenLote", new DictamenLoteRequestDto());

		// 🔹 Cargar listas en el Model para que Thymeleaf pueda renderizar los <select>
		model.addAttribute("ordenesProduccion", ordenProduccionService.listarOrden());
		model.addAttribute("usuariosInspectores", usuarioService.listarUsuarios()); // O el método equivalente de tu
																					// API/Service

		return "/dictamenLote/crearDictamenLote";
	}

	@PostMapping("/guardar")
	public String guardarDictamenLote(@ModelAttribute DictamenLoteRequestDto dictamenLote) {
		servicioAPI.guardarDictamenLote(dictamenLote);
		return "redirect:/dictamenLote";
	}

	// Ventana de revision: muestra el dictamen + el ensayo del lote, con botones
	// Aceptar/Rechazar.
	@GetMapping("/{idDictamen}/revisar")
	public String leerpaginaRevisar(@PathVariable int idDictamen, Model model) {
		model.addAttribute("dictamen", servicioAPI.obtenerConEnsayo(idDictamen));
		return "/dictamenLote/revisarDictamenLote";
	}

	@PostMapping("/{idDictamen}/aceptar")
	public String aceptar(@PathVariable int idDictamen) {
		servicioAPI.aceptarDictamen(idDictamen);
		return "redirect:/dictamenLote";
	}

	@PostMapping("/{idDictamen}/rechazar")
	public String rechazar(@PathVariable int idDictamen, @RequestParam String motivo) {
		servicioAPI.rechazarDictamen(idDictamen, motivo);
		return "redirect:/dictamenLote";
	}

	@GetMapping("/editarDictamenLote")
	public String leerpaginaeditar() {
		return "/dictamenLote/editarDictamenLote";
	}

}