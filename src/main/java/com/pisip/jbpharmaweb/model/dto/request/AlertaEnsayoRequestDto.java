package com.pisip.jbpharmaweb.model.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AlertaEnsayoRequestDto {
	private Long idAlerta;
	private Long idValidacion;
	private String tipoAlerta;
	private String destinatario;
	private String asunto;
	private String mensaje;
	private String estadoEnvio;
	private LocalDateTime fechaGeneracion;
	private LocalDateTime fechaEnvio;
}
