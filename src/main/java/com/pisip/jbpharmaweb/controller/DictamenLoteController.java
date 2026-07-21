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

import com.pisip.jbpharmaweb.model.dto.request.DictamenLoteRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;
import com.pisip.jbpharmaweb.service.IDictamenLoteService;

@Controller
@RequestMapping("/dictamenLote")
public class DictamenLoteController {

	@Autowired
	private IDictamenLoteService servicioAPI;

	@GetMapping
	public String leerpagina(Model model) {
		List<DictamenLoteResponseDto> datosAPI = servicioAPI.listarDictamenLote();
		model.addAttribute("listadictamenlote", datosAPI);
		return "/dictamenLote/listarDictamenLote";
	}

	@GetMapping("/crearDictamenLote")
	public String leerpaginacrear(Model model) {
		model.addAttribute("dictamenLote", new DictamenLoteRequestDto());
		return "/dictamenLote/crearDictamenLote";
	}

	@PostMapping("/guardar")
	public String guardarDictamenLote(@ModelAttribute DictamenLoteRequestDto dictamenLote) {
		servicioAPI.guardarDictamenLote(dictamenLote);
		return "redirect:/dictamenLote";
	}

	// Ventana de revision: muestra el dictamen + el ensayo del lote, con botones Aceptar/Rechazar.
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
