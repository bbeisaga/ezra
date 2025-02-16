package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;



@Entity
@Table(name = "productos")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	
	private String tamanio;
	
	private Float peso;
	
	private String marca;
	
	private String modelo;
	
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
	
	
	private Integer colorId;
	
	private Integer materialId;

	private Integer origenId;
	
	private Integer empaqueId;
	
	private Integer categoriaId;

	

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

	
	
	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getCostoUnitarioEmpaque() {
		return costoUnitarioEmpaque;
	}

	public void setCostoUnitarioEmpaque(BigDecimal costoUnitarioEmpaque) {
		this.costoUnitarioEmpaque = costoUnitarioEmpaque;
	}



	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getOrigenId() {
		return origenId;
	}

	public void setOrigenId(Integer origenId) {
		this.origenId = origenId;
	}

	public Integer getEmpaqueId() {
		return empaqueId;
	}

	public void setEmpaqueId(Integer empaqueId) {
		this.empaqueId = empaqueId;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}



	private static final long serialVersionUID = 1L;
}
