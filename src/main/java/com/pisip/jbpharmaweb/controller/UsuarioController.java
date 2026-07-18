package com.pisip.jbpharmaweb.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO;
import com.pisip.jbpharmaweb.model.dto.response.RolResponseDto;
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
	    List<RolResponseDto> listaRoles = servicioAPI.listarRoles();
	    
	    // Convertimos la lista de roles en un mapa [ID: Nombre] para buscar fácil en Thymeleaf
	    Map<Integer, String> mapaRoles = listaRoles.stream()
	        .collect(Collectors.toMap(RolResponseDto::getIdRol, RolResponseDto::getNombreRol));
	    
	    model.addAttribute("listausuarios", datosAPI);
	    model.addAttribute("mapaRoles", mapaRoles); // Pasamos el mapa a la vista
	    
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
	public String leerpaginaeditar(@RequestParam("idUsuario") int idUsuario, Model model) {
	    Optional<UsuarioResponseDTO> usuarioOpt = servicioAPI.obtenerUsuarioPorId(idUsuario);
	    
	    if (usuarioOpt.isEmpty()) {
	        return "redirect:/usuario?error=UsuarioNoEncontrado";
	    }

	    UsuarioResponseDTO res = usuarioOpt.get();
	    
	    // Mapeamos los datos de la respuesta al DTO del formulario
	    UsuarioRequestDTO formDto = new UsuarioRequestDTO();
	    formDto.setIdUsuario(res.getIdUsuario());
	    formDto.setNombre(res.getNombre());
	    formDto.setCorreo(res.getCorreo());
	    formDto.setEstadoUsuario(res.isEstadoUsuario());
	    formDto.setIdRol(res.getIdRol());
	    // La contraseña suele dejarse vacía o tratarse aparte por seguridad

	    model.addAttribute("usuario", formDto);
	    model.addAttribute("roles", servicioAPI.listarRoles()); // Necesario para el <select>
	    
	    return "/usuario/editarusuario";
	}

	@PostMapping("/actualizar")
	public String actualizarUsuario(@ModelAttribute UsuarioRequestDTO usuario) {
	    servicioAPI.actualizarUsuario(usuario.getIdUsuario(), usuario);
	    return "redirect:/usuario";
	}
	
	@PostMapping("/eliminar/{idUsuario}")
	public String eliminarUsuario(@PathVariable int idUsuario, RedirectAttributes redirectAttributes) {
	    try {
	        servicioAPI.eliminarUsuario(idUsuario);
	        redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el usuario.");
	    }
	    return "redirect:/usuario"; // Redirige de vuelta al listado
	}

}
