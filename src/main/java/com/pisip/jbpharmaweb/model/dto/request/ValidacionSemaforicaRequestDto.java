package com.pisip.jbpharmaweb.model.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ValidacionSemaforicaRequestDto {
	private Long idValidacion;
	private Long idVariable;
	private Integer idParametro;
	private String resultado;
	private String mensaje;
	private LocalDateTime fechaValidacion;
}
