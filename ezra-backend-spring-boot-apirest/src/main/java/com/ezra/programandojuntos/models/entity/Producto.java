package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo; 

	private String nombre; //enlace permanente
	
	private String descripcion;
	
	private String medidas;
	
	private String peso;
	
	private String imagen;
	
//	private String marca;
//	
//	private String modelo;
	
	@Column(name = "umbral_poca_cantidad")//muestra la cantidad restante del inventario cuando se ha baja Ejem. Solo quedan 2 exitencias
	private Long unbralPocaCantidad; 
	
	@Column(name = "umbral_agotada_cantidad") //Oculta del catalogo los articulos agotados
	private Long unbralAgotadaCantidad; 
	
	@Column(name = "cantidad_stock") //muestra la cantidad restante del inventario Ejem. 12 exitencias
	private Long cantidadStock; 
	
	@Column(name = "min_cantidad_pedido")
	private Long minCantidadPedido; 
	
	@Column(name = "max_cantidad_pedido")
	private Long maxCantidadPedido; 
	
	@Column(name = "grupos_de")
	private Long gruposDe; 
	
	@Column(name = "costo_unitario")
	private BigDecimal costoUnitario; /*cuenta cuesta hacer el producto*/
	
	@Column(name = "costo_unitario_empaque")
	private BigDecimal costoUnitarioEmpaque; /*cuenta cuesta hacer el producto*/
	
	@Column(name = "precio_bruto")
	private BigDecimal precioBruto; /*cubriri costo mas beneficio*/
	
	@Column(name = "precio_bruto_rebajado")
	private BigDecimal precioBrutoRebajado; /*cubriri costo mas beneficio*/
	
	@Column(name = "precio_neto")
	private BigDecimal precioNeto; /*Es el total mas impuestos*/
	
	@Column(name = "precio_neto_reabajado")
	private BigDecimal precioNetoRabajado; /*Es el total mas impuestos*/
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@Column(name = "fch_precio_rebajado_desde")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPrecioRebajadoDesde;
	
	@Column(name = "fch_precio_rebajado_hasta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPrecioRebajadoHasta;
	
	//@Column(name = "productos_relacionados")
	//private String productosRelacionados; //ventas dirigidas(prod sustituto por la calidad, mas caros)
	
	//@Column(name = "productos_promocion")
	//private String productosPromocion; //ventas cruzadas(prod adicionales al actual que podrian tener promocion)
	
	
	@Column(name = "visible_en_tienda")
	private boolean visibleEnTienda;
	
	@Column(name = "activo")
	private boolean activo;
	
	//@NotNull(message = "Tipo documento no puede ser vacio")
	@JoinColumn(name = "color_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Color color;
	
	@JoinColumn(name = "material_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Material material;
	
	@JoinColumn(name = "categoria_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Categoria categoria;
	
	@JoinColumn(name = "uso_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Uso uso;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}


	private static final long serialVersionUID = 1L;
}
