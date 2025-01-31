package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	
	@Column(name = "costo_unitario")
	private BigDecimal costoUnitario; /*cuenta cuesta hacer el producto*/
	
	@Column(name = "precio_bruto")
	private BigDecimal precioBruto; /*cubriri costo mas beneficio*/
	
	@Column(name = "precio_neto")
	private BigDecimal precioNeto; /*Es el total mas impuestos*/
	
	
	//private Double precio; /*esto deberá borrarse*/

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

//	public Double getPrecio() {
//		return precio;
//	}
//
//	public void setPrecio(Double precio) {
//		this.precio = precio;
//	}

	public BigDecimal getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(BigDecimal costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public BigDecimal getPrecioBruto() {
		return precioBruto;
	}

	public void setPrecioBruto(BigDecimal precioBruto) {
		this.precioBruto = precioBruto;
	}

	public BigDecimal getPrecioNeto() {
		return precioNeto;
	}

	public void setPrecioNeto(BigDecimal precioNeto) {
		this.precioNeto = precioNeto;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	private static final long serialVersionUID = 1L;
}
