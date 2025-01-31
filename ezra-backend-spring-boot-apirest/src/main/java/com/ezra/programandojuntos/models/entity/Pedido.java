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
@Table(name = "pedidos")
public class Pedido implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String observacion;
	@Column(name = "costo_total")
	private BigDecimal costoTotal;
	
	@Column(name = "precio_bruto_total")
	private BigDecimal precioBrutoTotal;
	
	@Column(name = "precio_neto_total")
	private BigDecimal precioNetoTotal;
	
	@Column(name = "pago_bruto_total")
	private BigDecimal pagoBrutoTotal;
	
	@Column(name = "pago_neto_total")
	private BigDecimal pagoNetoTotal;
	
	@Column(name = "vuelto_neto_total")
	private BigDecimal vueltoNetoTotal;

	@Column(name = "saldo_pedido")
	private BigDecimal saldoPedido;
	
	private boolean aceptado;
	private boolean vencido;
	private boolean pagado;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@NotNull(message = "no puede estar vacio")
	@Column(name = "entregado_en")
	@Temporal(TemporalType.DATE)
	private Date entregadoEn;
	
	@NotNull(message = "Estado pedido no puede ser vacio")
	
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
	
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "pedido" , cascade = CascadeType.ALL)
	private List<Movimiento> movimientos;

	public Pedido() {
		items = new ArrayList<>();
		movimientos = new ArrayList<>();
	}

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


	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItems() {
		return items;
	}

	public void setItems(List<ItemPedido> items) {
		this.items = items;
	}
	
	
	
	public BigDecimal getVueltoNetoTotal() {
		return vueltoNetoTotal;
	}

	public void setVueltoNetoTotal(BigDecimal vueltoNetoTotal) {
		this.vueltoNetoTotal = vueltoNetoTotal;
	}

	public Date getEntregadoEn() {
		return entregadoEn;
	}



	public BigDecimal getPagoBrutoTotal() {
		return pagoBrutoTotal;
	}

	public void setPagoBrutoTotal(BigDecimal pagoBrutoTotal) {
		this.pagoBrutoTotal = pagoBrutoTotal;
	}

	public BigDecimal getPagoNetoTotal() {
		return pagoNetoTotal;
	}

	public void setPagoNetoTotal(BigDecimal pagoNetoTotal) {
		this.pagoNetoTotal = pagoNetoTotal;
	}

	public BigDecimal getSaldoPedido() {
		return saldoPedido;
	}

	public void setSaldoPedido(BigDecimal saldoPedido) {
		this.saldoPedido = saldoPedido;
	}

	public void setEntregadoEn(Date entregadoEn) {
		this.entregadoEn = entregadoEn;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}



	public EstadoPedido getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(EstadoPedido estadoPedido) {
		this.estadoPedido = estadoPedido;
	}



	public boolean isVencido() {
		return vencido;
	}

	public void setVencido(boolean vencido) {
		this.vencido = vencido;
	}


	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}


	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}


	public BigDecimal getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(BigDecimal costoTotal) {
		this.costoTotal = costoTotal;
	}

	public BigDecimal getPrecioBrutoTotal() {
		return precioBrutoTotal;
	}

	public void setPrecioBrutoTotal(BigDecimal precioBrutoTotal) {
		this.precioBrutoTotal = precioBrutoTotal;
	}

	public BigDecimal getPrecioNetoTotal() {
		return precioNetoTotal;
	}

	public void setPrecioNetoTotal(BigDecimal precioNetoTotal) {
		this.precioNetoTotal = precioNetoTotal;
	}


	private static final long serialVersionUID = 1L;
}
