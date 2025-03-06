package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
public class Pedido implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "tipo_pedido_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoPedido tipoPedido;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "estado_pedido_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private EstadoPedido estadoPedido;

	@JsonIgnoreProperties(value={"pedidos", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "pedido_id")
	private List<ItemPedido> items;
	
	@JsonIgnoreProperties({"pedido", "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "pedido" , cascade = CascadeType.ALL)
	private List<Movimiento> movimientos;
	
	
	@Column(name = "costo_bruto_total")
	private BigDecimal costoBrutoTotal;
	
	@Column(name = "costo_neto_total")
	private BigDecimal costoNetoTotal;
	
	@Column(name = "precio_bruto_total")
	private BigDecimal precioBrutoTotal;
	
	@Column(name = "precio_neto_total")
	private BigDecimal precioNetoTotal;
	
	@Column(name = "pago_total")
	private BigDecimal pagoTotal;
	
	@Column(name = "vuelto_total")
	private BigDecimal vueltoTotal;

	@Column(name = "saldo_pedido")
	private BigDecimal saldoPedido;
	
//	@Column(name = "saldo_bruto_pedido")
//	private BigDecimal saldoBrutoPedido;
	
	private boolean aceptado;
	private boolean vencido;
	private boolean pagado;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	//@NotNull(message = "no puede estar vacio")
	@Column(name = "entregado_en")
	@Temporal(TemporalType.DATE)
	private Date entregadoEn;
	
	//@NotNull(message = "no puede estar vacio")
	@Column(name = "adquirido_en")
	@Temporal(TemporalType.DATE)
	private Date adquiridoEn;
	
	private String observacion;


	public Pedido() {
		items = new ArrayList<>();
		movimientos = new ArrayList<>();
	}

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	private static final long serialVersionUID = 1L;

	
}
