package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pisip.jbpharmaweb.model.dto.response.RolResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IEmailService;
import com.pisip.jbpharmaweb.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/autenticacion")
public class LoginController {

	@Autowired
	private IUsuarioService servicioAPI;

	@Autowired
	private IEmailService emailService;

	// Almacenamiento temporal de tokens de restablecimiento (token -> idUsuario)
	private static final Map<String, Integer> TOKENS_RESTABLECIMIENTO = new ConcurrentHashMap<>();

	@GetMapping
	public String leerpagina(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "forbidden", required = false) String forbidden,
			@RequestParam(value = "resetSuccess", required = false) String resetSuccess, Model model) {

		if ("unauthorized".equals(error)) {
			model.addAttribute("mensajeError", "Debes iniciar sesión para acceder al sistema.");
		} else if ("true".equals(error)) { // <-- AGREGA ESTE ELSE IF
			model.addAttribute("mensajeError", "Usuario o contraseña incorrectos.");
		} else if (forbidden != null) {
			model.addAttribute("mensajeError", "No tienes permisos para acceder a este módulo.");
		} else if (resetSuccess != null) {
			model.addAttribute("mensajeExito", "Contraseña actualizada correctamente. Inicia sesión.");
		}

		return "autenticacion/login";
	}

	@GetMapping("/logout")
	public String cerrarSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/autenticacion?logout=true";
	}

	@PostMapping("/login")
	public String procesarLogin(@RequestParam("username") String correo, @RequestParam("password") String contrasena,
			HttpSession session) {

		Optional<UsuarioResponseDTO> usuarioOpt = servicioAPI.autenticar(correo, contrasena);

		if (usuarioOpt.isPresent()) {
			UsuarioResponseDTO usuario = usuarioOpt.get();

			List<RolResponseDto> listaRoles = servicioAPI.listarRoles();
			Map<Integer, String> mapaRoles = listaRoles.stream()
					.collect(Collectors.toMap(RolResponseDto::getIdRol, RolResponseDto::getNombreRol));

			String nombreRol = mapaRoles.getOrDefault(usuario.getIdRol(), "").toUpperCase();

			session.setAttribute("usuarioLogueado", usuario.getCorreo());
			session.setAttribute("nombreUsuario", usuario.getNombre());
			session.setAttribute("rolUsuario", nombreRol);

			switch (nombreRol) {
			case "ADMINISTRADOR":
				return "redirect:/usuario";
			case "SUPERVISOR":
				return "redirect:/parametrocalidad";
			case "GERENTE":
				return "redirect:/planproduccion";
			case "ANALISTA":
				return "redirect:/ensayos";
			default:
				return "redirect:/autenticacion?error=true";
			}
		} else {
			return "redirect:/autenticacion?error=true";
		}
	}

	// =========================================================================
	// RECUPERACIÓN DE CONTRASEÑA POR EMAIL
	// =========================================================================

	@GetMapping("/recuperacion")
	public String leerPaginaRecuperacion() {
		return "autenticacion/recuperacion";
	}

	// 1. Validar correo, generar token y enviar el correo con el enlace
	@PostMapping("/validar-correo")
	public String procesarValidacionCorreo(@RequestParam("correo") String correo, HttpServletRequest request,
			Model model) {
		List<UsuarioResponseDTO> usuarios = servicioAPI.listarUsuarios();
		Optional<UsuarioResponseDTO> usuarioEncontrado = usuarios.stream()
				.filter(u -> u.getCorreo().equalsIgnoreCase(correo)).findFirst();

		if (usuarioEncontrado.isPresent()) {
			UsuarioResponseDTO usuario = usuarioEncontrado.get();

			// Generar Token único
			String token = UUID.randomUUID().toString();
			TOKENS_RESTABLECIMIENTO.put(token, usuario.getIdUsuario());

			// Construir enlace dinamico (ej:
			// http://localhost:8080/autenticacion/restablecer?token=...)
			String urlBase = request.getRequestURL().toString().replace(request.getRequestURI(),
					request.getContextPath());
			String enlaceRestablecimiento = urlBase + "/autenticacion/restablecer?token=" + token;

			// Enviar Email
			try {
				emailService.enviarCorreoRestablecimiento(usuario.getCorreo(), enlaceRestablecimiento);
				model.addAttribute("mensajeExito",
						"Se ha enviado un correo con las instrucciones para restablecer tu contraseña.");
			} catch (Exception e) {
				// 👇 AGREGA ESTA LÍNEA PARA VER EL ERROR EN LA CONSOLA
				e.printStackTrace();

				model.addAttribute("errorCorreo", "Error al enviar el correo. Por favor intente más tarde.");
			}
		} else {
			model.addAttribute("errorCorreo", "El correo ingresado no se encuentra registrado.");
		}

		return "autenticacion/recuperacion";
	}

	// 2. Pantalla para cambiar la contraseña (Validada por TOKEN)
	@GetMapping("/restablecer")
	public String leerPaginaRestablecer(@RequestParam(value = "token", required = false) String token, Model model) {
		// Verificar si el token existe en el mapa
		if (token == null || !TOKENS_RESTABLECIMIENTO.containsKey(token)) {
			return "redirect:/autenticacion/recuperacion";
		}

		model.addAttribute("token", token);
		return "autenticacion/restablecer";
	}

	// 3. Procesar la actualización con el token recibido
	@PostMapping("/actualizar-contrasena")
	public String actualizarContrasena(@RequestParam("token") String token,
			@RequestParam("nuevaContrasena") String nuevaContrasena) {

		Integer idUsuario = TOKENS_RESTABLECIMIENTO.get(token);

		if (idUsuario == null) {
			return "redirect:/autenticacion/recuperacion";
		}

		Optional<UsuarioResponseDTO> opt = servicioAPI.obtenerUsuarioPorId(idUsuario);
		if (opt.isPresent()) {
			UsuarioResponseDTO actual = opt.get();

			com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO req = new com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO();
			req.setIdUsuario(actual.getIdUsuario());
			req.setNombre(actual.getNombre());
			req.setCorreo(actual.getCorreo());
			req.setEstadoUsuario(actual.isEstadoUsuario());
			req.setIdRol(actual.getIdRol());
			req.setContrasenaHash(nuevaContrasena);

			servicioAPI.actualizarUsuario(idUsuario, req);
		}

		// Invalidar el token para que no se vuelva a usar
		TOKENS_RESTABLECIMIENTO.remove(token);

		return "redirect:/autenticacion?resetSuccess=true";
	}
}