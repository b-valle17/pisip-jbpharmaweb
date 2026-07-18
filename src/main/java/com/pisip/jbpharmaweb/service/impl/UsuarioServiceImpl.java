package com.pisip.jbpharmaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pisip.jbpharmaweb.model.dto.request.LoginRequestDto;
import com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO;
import com.pisip.jbpharmaweb.model.dto.response.RolResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	private final WebClient webClient;

	public UsuarioServiceImpl(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<UsuarioResponseDTO> listarUsuarios() {
		// TODO Auto-generated method stub
		return webClient.get().uri("/usuarios").retrieve().bodyToFlux(UsuarioResponseDTO.class).collectList().block();
	}

	@Override
	public Optional<UsuarioResponseDTO> autenticar(String correo, String contrasenaHash) {
		// 1. Instanciamos el DTO de petición con las credenciales
		LoginRequestDto loginRequest = new LoginRequestDto(correo, contrasenaHash);

		try {
			// 2. Realizamos la llamada POST a la API REST de login
			UsuarioResponseDTO response = webClient.post().uri("/usuarios/login") // Ajusta la ruta si tu WebClient base
																					// no incluye "/api"
					.bodyValue(loginRequest) // Enviamos el JSON en el cuerpo
					.retrieve().bodyToMono(UsuarioResponseDTO.class).block(); // Bloqueamos síncronamente para Spring
																				// MVC tradicional

			return Optional.ofNullable(response);

		} catch (WebClientResponseException.Unauthorized | WebClientResponseException.NotFound e) {
			// Si las credenciales son incorrectas o la API lanza un error de no
			// autorizado/no encontrado
			return Optional.empty();
		} catch (Exception e) {
			// Cualquier otro error de red o de servidor
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public void guardarUsuario(UsuarioRequestDTO nuevo) {
		webClient.post().uri("/usuarios").bodyValue(nuevo).retrieve().toBodilessEntity().block();

	}

	@Override
	public List<RolResponseDto> listarRoles() {
		return webClient.get().uri("/roles").retrieve().bodyToFlux(RolResponseDto.class).collectList().block();
	}

	@Override
	public Optional<UsuarioResponseDTO> obtenerUsuarioPorId(int idUsuario) {
		try {
	        UsuarioResponseDTO usuario = webClient.get()
	            .uri("/usuarios/{idUsuario}", idUsuario)
	            .retrieve()
	            .bodyToMono(UsuarioResponseDTO.class)
	            .block();
	        return Optional.ofNullable(usuario);
	    } catch (Exception e) {
	        return Optional.empty();
	    }
	}

	@Override
	public void actualizarUsuario(int idUsuario, UsuarioRequestDTO usuarioActualizado) {
		webClient.put()
        .uri("/usuarios/{idUsuario}", idUsuario)
        .bodyValue(usuarioActualizado)
        .retrieve()
        .toBodilessEntity()
        .block();
		
	}

	@Override
	public void eliminarUsuario(int idUsuario) {
		webClient.delete()
        .uri("/usuarios/{idUsuario}", idUsuario)
        .retrieve()
        .toBodilessEntity() // Usamos esto porque el backend responde con un "Void" (vacío)
        .block(); // Espera a que la operación termine de forma síncrona
		
	}


}
