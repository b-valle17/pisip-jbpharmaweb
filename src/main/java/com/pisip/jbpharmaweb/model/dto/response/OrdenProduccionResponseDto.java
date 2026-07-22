package com.pisip.jbpharmaweb.model.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class OrdenProduccionResponseDto {
	
	private Integer idOrden;
	private Integer idPlan;
	private Integer idProducto;
	private Integer idUsuario;
	private String numeroLote;
	private BigDecimal cantidadLote;
	private Date fechaInicio;
	private Date fechaFin;
	private String estado;
	private String codigoPlan;
	
	private ProductoResponseDto producto;
    private PlanProduccionResponseDto planProduccion;
    private UsuarioResponseDTO usuario;
}
