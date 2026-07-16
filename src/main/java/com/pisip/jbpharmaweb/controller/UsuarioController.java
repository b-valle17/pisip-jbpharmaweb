package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService servicioAPI;
	@GetMapping
	public String leerpagina(Model model) {
		List<UsuarioResponseDTO> datosAPI = servicioAPI.listarUsuarios();
		model.addAttribute("listausuarios", datosAPI);
		return "/usuario/listarusuarios";
	}
	
	@GetMapping("/crearusuario")
	public String leerpaginacrear(Model model) {
		model.addAttribute("usuario", new UsuarioRequestDTO());
		model.addAttribute("roles", servicioAPI.listarRoles());
		return "/usuario/crearusuario";
	}
	
	@PostMapping("/guardar")
	public String guardarUsuario(@ModelAttribute UsuarioRequestDTO usuario) {
		servicioAPI.guardarUsuario(usuario);
		return "redirect:/usuario";
	}
	
	@GetMapping("/editarusuario")
	public String leerpaginaeditar() {
		return "/usuario/editarusuario";
	}

}
