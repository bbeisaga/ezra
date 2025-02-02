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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "caja_usuarios")
public class CajaUsuario implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha_apertura")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaApertura;
	
	@Column(name = "fecha_cierre")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCierre;	
	
	private BigDecimal ingresoEsperado;
	
//	private BigDecimal ingresoPorConteo;
	
	private BigDecimal egresoEsperado;
	
	private BigDecimal saldoCaja;
	
	private BigDecimal saldoPorConteo;

	private boolean activa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "caja_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Caja caja;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Usuario usuario;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cajaUsuario", cascade = CascadeType.ALL)
	private List<MovimientoVenta> movimientosVenta;
	
	
	@PrePersist
	public void prePersist() {
		this.fechaApertura = new Date();
	}
//	
//	@PreUpdate
//	public void preUpdate() {
//		this.fechaCierre = new Date();
//	}

	public CajaUsuario() {
		this.movimientosVenta = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public BigDecimal getIngresoEsperado() {
		return ingresoEsperado;
	}

	public void setIngresoEsperado(BigDecimal ingresoEsperado) {
		this.ingresoEsperado = ingresoEsperado;
	}

//	public BigDecimal getIngresoPorConteo() {
//		return ingresoPorConteo;
//	}
//
//	public void setIngresoPorConteo(BigDecimal ingresoPorConteo) {
//		this.ingresoPorConteo = ingresoPorConteo;
//	}

	public BigDecimal getEgresoEsperado() {
		return egresoEsperado;
	}

	public void setEgresoEsperado(BigDecimal egresoEsperado) {
		this.egresoEsperado = egresoEsperado;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public List<MovimientoVenta> getMovimientosVenta() {
		return movimientosVenta;
	}

	public void setMovimientosVenta(List<MovimientoVenta> movimientosVenta) {
		this.movimientosVenta = movimientosVenta;
	}

	public BigDecimal getSaldoCaja() {
		return saldoCaja;
	}

	public void setSaldoCaja(BigDecimal saldoCaja) {
		this.saldoCaja = saldoCaja;
	}
	
	
	public BigDecimal getSaldoPorConteo() {
		return saldoPorConteo;
	}

	public void setSaldoPorConteo(BigDecimal saldoPorConteo) {
		this.saldoPorConteo = saldoPorConteo;
	}

	private static final long serialVersionUID = 1L;
}
