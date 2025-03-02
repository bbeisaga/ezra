package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movimientos_venta")
@Getter
@Setter
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

