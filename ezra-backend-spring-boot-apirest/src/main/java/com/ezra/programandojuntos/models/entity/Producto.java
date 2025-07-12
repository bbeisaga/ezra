package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;




@Entity
@Table(name = "productos")
@Data
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
	
	@Column(name="umbral_poca_cantidad", columnDefinition="long default 1")
	private Long umbralPocaCantidad; 
	
	@Column(name="umbral_cantidad_agotada", columnDefinition="long default 0")
	private Long umbralCantidadAgotada; 

	@Column(name="cantidad_vendidos", columnDefinition="long default 0")
	private Long cantidadVendidos; 

	@Column(name="cantidad_stock", columnDefinition="long default 0")
	private Long cantidadStock; 
	
	@Column(name="min_cantidad_pedido", columnDefinition="long default 1")
	private Long minCantidadPedido; 
	
	@Column(name="max_cantidad_pedido", columnDefinition="long default 100000")
	private Long maxCantidadPedido; 
	
	@Column(name="grupos_de", columnDefinition="long default 1")
	private Long gruposDe; 
	 
	@Column(name="costo_unitario", columnDefinition="Decimal(10,2) default '0'")
	private BigDecimal costoUnitario; 
	
//	@Column(name="costo_personalizacion", columnDefinition="Decimal(10,2) default '0'")
//	private BigDecimal costoPersonalizacion;
	
	@Column(name="impuesto_igv", columnDefinition="Decimal(10,2) default '18'")
	private Float impuestoIgv;
	
//	@Column(name="margen_ganancia", columnDefinition="Decimal(10,2) default '0'")
//	private Float margenGanancia;
	
//	@Column(name = "costo_unitario_empaque")
//	private BigDecimal costoUnitarioEmpaque; 
	
//	@Column(name = "precio_bruto")
//	private BigDecimal precioBruto; /*cubriri costo mas beneficio*/
	
//	@Column(name = "precio_bruto_rebajado")
//	private BigDecimal precioBrutoRebajado; /*cubriri costo mas beneficio*/
	
//	@Column(name="precio_neto", columnDefinition="Decimal(10,2) default '0'")
//	private BigDecimal precioNeto; /*Es el total mas impuestos*/
	
//	@Column(name = "precio_neto_reabajado")
//	private BigDecimal precioNetoRabajado=BigDecimal.valueOf(3); /*Es el total mas impuestos*/
	
	@Column(name = "fch_ult_real_compra")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fchUltRealCompra;
	
	@Column(name = "fch_ult_real_venta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fchUltRealVenta;
	
//	@Temporal(TemporalType.DATE)
//	private Date fchProximoIngreso;
	
	//fecha creacion del producto
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@Column(name = "update_at")
	@Temporal(TemporalType.DATE)
	private Date updateAt;
	
//	@Column(name = "fch_precio_rebajado_desde")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date fechaPrecioRebajadoDesde;
//	
//	@Column(name = "fch_precio_rebajado_hasta")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date fechaPrecioRebajadoHasta;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "estado_producto_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private EstadoProducto estadoProducto;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	private List<MargenProducto> margenesProducto;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateAt = new Date();
	}

	private static final long serialVersionUID = 1L;
}
