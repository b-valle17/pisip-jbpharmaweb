package com.pisip.jbpharmaweb.model.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EnsayoLaboratorioRequestDto {
	private Long idEnsayo;
	private Integer idOrden;
	private Integer idProducto;
	private String codigoEnsayo;
	private LocalDateTime fechaEnsayo;
	private String responsable;
	private String observacion;
	private String estado;
	private LocalDateTime creadoEn;
}
