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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.DictamenLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IDictamenLoteService;
import com.pisip.jbpharmaweb.service.IOrdenProduccionService;
import com.pisip.jbpharmaweb.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dictamenLote")
public class DictamenLoteController {

	private static final String ROL_SUPERVISOR = "SUPERVISOR";
	private static final String ROL_GERENTE = "GERENTE";

	@Autowired
	private IDictamenLoteService servicioAPI;

	@Autowired
	private IOrdenProduccionService ordenProduccionService;

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping
	public String leerpagina(Model model) {
		List<DictamenLoteResponseDto> datosAPI = servicioAPI.listarDictamenLote();
		List<UsuarioResponseDTO> listaUsuarios = usuarioService.listarUsuarios();
		List<OrdenProduccionResponseDto> listaOrden = ordenProduccionService.listarOrden();
		List<EnsayoLaboratorioResponseDto> ensayosPendientes = servicioAPI.listarEnsayosPendientes();

		Map<Integer, String> mapaUsuarios = listaUsuarios.stream()
				.collect(Collectors.toMap(UsuarioResponseDTO::getIdUsuario, UsuarioResponseDTO::getNombre));
		Map<Integer, String> mapaOrdenes = listaOrden.stream().collect(
				Collectors.toMap(OrdenProduccionResponseDto::getIdOrden, OrdenProduccionResponseDto::getNumeroLote));
		model.addAttribute("listadictamenlote", datosAPI);
		model.addAttribute("mapaUsuarios", mapaUsuarios);
		model.addAttribute("mapaOrdenes", mapaOrdenes);
		model.addAttribute("ensayospendientes", ensayosPendientes);
		return "dictamenLote/listarDictamenLote";
	}

	@GetMapping("/crearDictamenLote")
	public String leerpaginacrear(@RequestParam(required = false) Integer idOrden, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (esGerente(session)) {
			redirectAttributes.addFlashAttribute("error", "El rol Gerente no puede registrar dictámenes.");
			return "redirect:/dictamenLote";
		}
		DictamenLoteRequestDto dto = new DictamenLoteRequestDto();
		if (idOrden != null) {
			dto.setIdOrdenProduccion(idOrden);
		}
		model.addAttribute("dictamenLote", dto);
		model.addAttribute("ordenesProduccion", ordenProduccionService.listarOrden());
		model.addAttribute("usuariosInspectores", usuarioService.listarUsuarios());

		return "/dictamenLote/crearDictamenLote";
	}

	@PostMapping("/guardar")
	public String guardarDictamenLote(@ModelAttribute DictamenLoteRequestDto dictamenLote, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (esGerente(session)) {
			redirectAttributes.addFlashAttribute("error", "El rol Gerente no puede registrar dictámenes.");
			return "redirect:/dictamenLote";
		}
		servicioAPI.guardarDictamenLote(dictamenLote);
		return "redirect:/dictamenLote";
	}

	@GetMapping("/{idDictamen}/revisar")
	public String leerpaginaRevisar(@PathVariable int idDictamen, Model model) {
		model.addAttribute("dictamen", servicioAPI.obtenerConEnsayo(idDictamen));
		return "/dictamenLote/revisarDictamenLote";
	}

	@PostMapping("/{idDictamen}/aceptar")
	public String aceptar(@PathVariable int idDictamen, HttpSession session, RedirectAttributes redirectAttributes) {
		if (!esSupervisor(session)) {
			redirectAttributes.addFlashAttribute("error", "Solo el Supervisor puede aprobar un dictamen.");
			return "redirect:/dictamenLote/" + idDictamen + "/revisar";
		}
		servicioAPI.aceptarDictamen(idDictamen);
		return "redirect:/dictamenLote";
	}

	@PostMapping("/{idDictamen}/rechazar")
	public String rechazar(@PathVariable int idDictamen, @RequestParam String motivo, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (!esSupervisor(session)) {
			redirectAttributes.addFlashAttribute("error", "Solo el Supervisor puede rechazar un dictamen.");
			return "redirect:/dictamenLote/" + idDictamen + "/revisar";
		}
		servicioAPI.rechazarDictamen(idDictamen, motivo);
		return "redirect:/dictamenLote";
	}

	@GetMapping("/editarDictamenLote")
	public String leerpaginaeditar() {
		return "/dictamenLote/editarDictamenLote";
	}

	private boolean esGerente(HttpSession session) {
		Object rol = session.getAttribute("rolUsuario");
		return rol != null && ROL_GERENTE.equalsIgnoreCase(rol.toString());
	}

	private boolean esSupervisor(HttpSession session) {
		Object rol = session.getAttribute("rolUsuario");
		return rol != null && ROL_SUPERVISOR.equalsIgnoreCase(rol.toString());
	}

}