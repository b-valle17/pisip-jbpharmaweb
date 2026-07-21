package com.pisip.jbpharmaweb.model.dto.request;

import lombok.Data;

@Data
public class ProductoRequestDto {

	private Integer idProducto;

	private String nombreProducto;

	private String descripcion;
}