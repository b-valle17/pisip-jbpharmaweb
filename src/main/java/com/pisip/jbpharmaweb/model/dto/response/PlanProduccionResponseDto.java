package com.pisip.jbpharmaweb.model.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanProduccionResponseDto {
	
	private Integer idPlan;
	private String codigoPlan;
	private Integer mes;
	private Integer anio;
	private LocalDateTime fechaEmision;
	private String estado;
	private String descripcion;
	private Integer cantidadLotesEstimada;
	
	@JsonProperty("idUsuario")
    @JsonAlias({"fkUsuario", "fk_usuario", "id_usuario", "idUsuario"})
    private Integer idUsuario;
	
	private String nombreUsuario;
	private UsuarioResponseDTO usuario;

	public Integer getIdUsuario() {
		if (this.idUsuario != null) {
			return this.idUsuario;
		}
		if (this.usuario != null && this.usuario.getIdUsuario() > 0) {
			return this.usuario.getIdUsuario();
		}
		return null;
	}

	public String getNombreUsuario() {
		if (this.nombreUsuario != null && !this.nombreUsuario.trim().isEmpty()) {
			return this.nombreUsuario;
		}
		if (this.usuario != null && this.usuario.getNombre() != null) {
			return this.usuario.getNombre();
		}
		return null;
	}
}