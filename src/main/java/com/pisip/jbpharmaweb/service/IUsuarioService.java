package com.pisip.jbpharmaweb.service;

import java.util.List;
import java.util.Optional;

import com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO;
import com.pisip.jbpharmaweb.model.dto.response.RolResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;

public interface IUsuarioService {

	public List<UsuarioResponseDTO> listarUsuarios();

	Optional<UsuarioResponseDTO> autenticar(String correo, String contrasenaHash);

	public void guardarUsuario(UsuarioRequestDTO nuevo);
	
	List<RolResponseDto> listarRoles();
	
	Optional<UsuarioResponseDTO> obtenerUsuarioPorId(int idUsuario);
	
	void actualizarUsuario(int idUsuario, UsuarioRequestDTO usuarioActualizado);
	
	public void eliminarUsuario(int idUsuario);

}
