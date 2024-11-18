package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
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
	
	private Double total;
	
	private Double pago;
	
	private Double apagar;
	
	private Double saldo;
	
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
	@ManyToOne(fetch = FetchType.LAZY)
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

	public Pedido() {
		items = new ArrayList<>();
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
	
	
//	public Double calcularTotal() {
//		Double total = 0.00;
//		for (ItemPedido item : items) {
//			total += item.getImporte();
//		}
//		return total;
//	}
			
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}



//	public void setSaldo(Double saldo) {		
//		this.saldo = saldo;
//	}
//
//	public Double getSaldo() {
//		return this.getTotal() - this.getAcuenta();
//	}
	

	public Double getPago() {
		return pago;
	}

	public void setPago(Double pago) {
		this.pago = pago;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	
	
	public Date getEntregadoEn() {
		return entregadoEn;
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




	public Double getApagar() {
		return apagar;
	}

	public void setApagar(Double apagar) {
		this.apagar = apagar;
	}




	private static final long serialVersionUID = 1L;
}
