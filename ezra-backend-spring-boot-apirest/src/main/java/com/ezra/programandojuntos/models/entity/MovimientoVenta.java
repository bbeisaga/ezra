package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "movimientos_venta")
public class MovimientoVenta implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties(value={"movimientosVenta", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;
	
	@JsonIgnoreProperties(value={"movimientosVenta", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@ManyToOne(fetch = FetchType.LAZY)
	private CajaUsuario cajaUsuario;
	
	
	//@NotNull(message = "Estado pedido no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_pago_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoPago tipoPago;
	
	
	//@NotNull(message = "Estado pedido no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_movimiento_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoMovimiento tipoMovimiento;
	
//	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "movimiento_id")
//	private List<ItemMovimiento> itemsMov;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@Column(name = "ingreso_dinero")
	private BigDecimal ingresoDinero;
	
	@Column(name = "egreso_dinero")
	private BigDecimal egresoDinero;
	
//	@Column(name = "saldo_dinero")
//	private BigDecimal saldoDinero;
	
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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public CajaUsuario getCajaUsuario() {
		return cajaUsuario;
	}

	public void setCajaUsuario(CajaUsuario cajaUsuario) {
		this.cajaUsuario = cajaUsuario;
	}


	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
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

