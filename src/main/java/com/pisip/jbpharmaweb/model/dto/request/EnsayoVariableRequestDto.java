package com.pisip.jbpharmaweb.model.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EnsayoVariableRequestDto {
	private Long idVariable;
	private Long idEnsayo;
	private String nombreVariable;
	private BigDecimal valorObtenido;
	private String unidadMedida;
	private LocalDateTime creadoEn;
}
