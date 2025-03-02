package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name = "productos")
public class Producto implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	
	private String medidas;
	
	private String peso;
	
//	private String marca;
//	
//	private String modelo;
	
	@Column(name = "cantidad_stock")
	private Long cantidadStock; /*cuenta cuesta hacer el producto*/
	
	@Column(name = "costo_unitario")
	private BigDecimal costoUnitario; /*cuenta cuesta hacer el producto*/
	
	@Column(name = "costo_unitario_empaque")
	private BigDecimal costoUnitarioEmpaque; /*cuenta cuesta hacer el producto*/
	
	
	@Column(name = "precio_bruto")
	private BigDecimal precioBruto; /*cubriri costo mas beneficio*/
	
	@Column(name = "precio_neto")
	private BigDecimal precioNeto; /*Es el total mas impuestos*/
	
	//private Double precio; /*esto deberá borrarse*/
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	//@NotNull(message = "Tipo documento no puede ser vacio")
	@JoinColumn(name = "color_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Color color;
	
	@JoinColumn(name = "material_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Material material;
	
	@JoinColumn(name = "categoria_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Categoria categoria;
	
	@JoinColumn(name = "uso_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Uso uso;
	
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

	public Long getCantidadStock() {
		return cantidadStock;
	}

	public void setCantidadStock(Long cantidadStock) {
		this.cantidadStock = cantidadStock;
	}



	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public BigDecimal getCostoUnitarioEmpaque() {
		return costoUnitarioEmpaque;
	}

	public void setCostoUnitarioEmpaque(BigDecimal costoUnitarioEmpaque) {
		this.costoUnitarioEmpaque = costoUnitarioEmpaque;
	}

	public String getMedidas() {
		return medidas;
	}

	public void setMedidas(String medidas) {
		this.medidas = medidas;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Uso getUso() {
		return uso;
	}

	public void setUso(Uso uso) {
		this.uso = uso;
	}

	private static final long serialVersionUID = 1L;
}
