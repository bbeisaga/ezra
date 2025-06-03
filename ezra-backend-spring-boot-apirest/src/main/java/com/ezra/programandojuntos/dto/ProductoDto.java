package com.ezra.programandojuntos.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.ezra.programandojuntos.models.entity.Categoria;
import com.ezra.programandojuntos.models.entity.Color;
import com.ezra.programandojuntos.models.entity.Material;
import com.ezra.programandojuntos.models.entity.Uso;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDto {
	private Long id;
	private String nombre; //enlace permanente
	private String descripcion;
//	private String medidas;
//	private String peso;
	private String imagen;
//	private Long unbralPocaCantidad; 
//	private Long unbralAgotadaCantidad; 
//	private Long cantidadStock; 
//	private Long minCantidadPedido; 
//	private Long maxCantidadPedido; 
//	private Long gruposDe; 
//	private BigDecimal costoUnitario; /*cuenta cuesta hacer el producto*/
//	private BigDecimal costoUnitarioEmpaque; /*cuenta cuesta hacer el producto*/
//	private BigDecimal precioBruto; /*cubriri costo mas beneficio*/
//	private BigDecimal precioBrutoRebajado; /*cubriri costo mas beneficio*/
//	private BigDecimal precioNeto; /*Es el total mas impuestos*/
//	private BigDecimal precioNetoRabajado; /*Es el total mas impuestos*/
//	private Date createAt;
//	private Date fechaPrecioRebajadoDesde;
//	private Date fechaPrecioRebajadoHasta;
//	private String productosRelacionados; //ventas dirigidas(prod sustituto por la calidad, mas caros)
//	private String productosPromocion; //ventas cruzadas(prod adicionales al actual que podrian tener promocion)
//	private Color color;
//	private Material material;
//	private Categoria categoria;
//	private Uso uso;
	


}
