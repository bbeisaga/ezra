package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movimientos")
@Getter
@Setter
public class Movimiento implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties(value={"movimientos", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;
	
	@JsonIgnoreProperties(value={"movimientos", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@ManyToOne(fetch = FetchType.LAZY)
	private CajaUsuario cajaUsuario;
	
	
	//@NotNull(message = "Estado pedido no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_pago_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoPago tipoPago;
	
	
	//@NotNull(message = "Estado pedido no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_movimiento_pedido_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoMovimientoPedido tipoMovimientoPedido;
	
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

