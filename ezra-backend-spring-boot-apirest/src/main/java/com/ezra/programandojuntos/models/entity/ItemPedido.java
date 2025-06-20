package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedidos_items")
@Getter
@Setter
public class ItemPedido implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long cantidad;
	
	private BigDecimal importe; /*cuenta cuesta hacer el producto*/

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	private Producto producto;
	
	private String descripcion;

	private String imagen;
//	public BigDecimal getImporte() {
//	return BigDecimal.valueOf( cantidad ).multiply( producto.getPrecioNeto() );				
//}
	

	private static final long serialVersionUID = 1L;
}
