package com.ezra.programandojuntos.models.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "margenes_producto")
@Data
public class MargenProducto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="min_cantidad", columnDefinition="long default 1")
	private Long minCantidad; 
	
	@Column(name="max_cantidad")
	private Long maxCantidad;
	
	@Column(name="margen", columnDefinition="Decimal(10,2) default '0'")
	private BigDecimal margen;
	
	@Column(name="precio_neto_sugerido", columnDefinition="Decimal(10,2) default '0'")
	private BigDecimal precioNetoSugerido; 
	
	@Column(name="precio_neto", columnDefinition="Decimal(10,2) default '0'")
	private BigDecimal precioNeto; 

}
