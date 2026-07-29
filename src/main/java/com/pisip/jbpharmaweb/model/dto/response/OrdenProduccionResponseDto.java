package com.pisip.jbpharmaweb.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdenProduccionResponseDto {
	
	private Integer idOrden;
	private Integer idPlan;
	private Integer idProducto;
	private String numeroLote;
	private BigDecimal cantidadLote;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private String estado;
	private String codigoPlan;
	
	@JsonProperty("idUsuario")
    @JsonAlias({"fkUsuario", "fk_usuario", "id_usuario", "idUsuario"})
    private Integer idUsuario;
	
	private String nombreProducto;
	private ProductoResponseDto producto;
    private PlanProduccionResponseDto planProduccion;
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

 	public String getNombreProducto() {
 		if (this.nombreProducto != null && !this.nombreProducto.trim().isEmpty()) {
 			return this.nombreProducto;
 		}
 		if (this.producto != null && this.producto.getNombreProducto() != null) {
 			return this.producto.getNombreProducto();
 		}
 		return null;
 	}

 	public String getCodigoPlan() {
 		if (this.codigoPlan != null && !this.codigoPlan.trim().isEmpty()) {
 			return this.codigoPlan;
 		}
 		if (this.planProduccion != null && this.planProduccion.getCodigoPlan() != null) {
 			return this.planProduccion.getCodigoPlan();
 		}
 		return null;
 	}
}