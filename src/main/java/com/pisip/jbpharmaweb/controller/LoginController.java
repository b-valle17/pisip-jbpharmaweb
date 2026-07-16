package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autenticacion")
public class LoginController {
	
	@GetMapping
	public String leerpagina() {
		return "autenticacion/login"; // Removida la barra inicial '/' que a veces causa problemas en Thymeleaf
	}
	
	@GetMapping("/recuperacion")
	public String leerpaginarecuperacion() {
		return "autenticacion/recuperacion";
	}

	// 💡 MÉTODO DE PRUEBA CON DATOS QUEMADOS
	@PostMapping("/login")
	public String procesarLogin(
			@RequestParam("username") String correo, 
			@RequestParam("password") String contrasena) {
		
		System.out.println("=================================================");
		System.out.println("   [CONSOLA] INTENTO DE INICIO DE SESIÓN");
		System.out.println("   Correo ingresado: " + correo);
		System.out.println("   Contraseña ingresada: " + contrasena);
		System.out.println("=================================================");

		// 🔑 DATOS QUEMADOS DE PRUEBA
		String correoValido = "pepe@gmail.com";
		String contrasenaValida = "123456";

		if (correoValido.equalsIgnoreCase(correo) && contrasenaValida.equals(contrasena)) {
			System.out.println("   STATUS: ¡Autenticación Exitosa! 🎉");
			System.out.println("   Redirigiendo al listado de usuarios...");
			System.out.println("=================================================");
			
			// Si es correcto, lo enviamos a la vista de usuarios
			return "redirect:/usuario";
		} else {
			System.out.println("   STATUS: Error de credenciales ❌");
			System.out.println("   Redirigiendo de vuelta al login...");
			System.out.println("=================================================");
			
			// Si falla, lo devolvemos al login con un parámetro de error en la URL
			return "redirect:/autenticacion?error=true";
		}
	}
}