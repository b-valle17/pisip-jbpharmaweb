package com.pisip.jbpharmaweb.model.dto.response;

import java.math.BigDecimal;
import java.util.Date;

public class OrdenProduccionResponseDto {
	
	private int idOrden;
	private String numeroLote;
	private BigDecimal cantidadLote;
	private Date fechaInicio;
	private Date fechaFin;
	private String estado;
	
	public int getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(int idOrden) {
		this.idOrden = idOrden;
	}
	public String getNumeroLote() {
		return numeroLote;
	}
	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}
	public BigDecimal getCantidadLote() {
		return cantidadLote;
	}
	public void setCantidadLote(BigDecimal cantidadLote) {
		this.cantidadLote = cantidadLote;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
