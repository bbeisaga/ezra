package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "movimientos_caja")
public class MovimientoCaja implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties(value={"movimientosCaja", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@ManyToOne(fetch = FetchType.LAZY)
	private CajaUsuario cajaUsuario;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_movimiento_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoMovimiento tipoMovimiento;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@Column(name = "ingreso_dinero")
	private BigDecimal ingresoDinero;
	
	@Column(name = "egreso_dinero")
	private BigDecimal egresoDinero;
	
	@Column(name = "descripcion")
	private String descripcion;
	
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



	public CajaUsuario getCajaUsuario() {
		return cajaUsuario;
	}

	public void setCajaUsuario(CajaUsuario cajaUsuario) {
		this.cajaUsuario = cajaUsuario;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}



	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public BigDecimal getIngresoDinero() {
		return ingresoDinero;
	}

	public void setIngresoDinero(BigDecimal ingresoDinero) {
		this.ingresoDinero = ingresoDinero;
	}

	public BigDecimal getEgresoDinero() {
		return egresoDinero;
	}

	public void setEgresoDinero(BigDecimal egresoDinero) {
		this.egresoDinero = egresoDinero;
	}

//	public BigDecimal getSaldoDinero() {
//		return saldoDinero;
//	}
//
//	public void setSaldoDinero(BigDecimal saldoDinero) {
//		this.saldoDinero = saldoDinero;
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

