package com.pisip.jbpharmaweb.model.dto.request;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class EnsayoLaboratorioRequestDto {
	private Long idEnsayo;
	private Integer idOrden;
	private Integer idProducto;
	private String codigoEnsayo;
	@DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm")
	private LocalDateTime fechaEnsayo;
	private String responsable;
	private String observacion;
	private String estado;
	@DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm")
	private LocalDateTime creadoEn;
}
