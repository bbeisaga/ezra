package com.ezra.programandojuntos.dto;

import java.math.BigDecimal;

import com.ezra.programandojuntos.enums.SortActiveCliente;
import com.ezra.programandojuntos.enums.SortDirection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoReporte {
	String nomApellRz;
	Long codigoPedido;
	String fechaCreacion;
	String fechaEntrega;
	String adquiridoEn;
	String estadoPedido;
	String descripcionPedido;
	BigDecimal costoBrutoTotal;
	BigDecimal costoNetoTotal;
	BigDecimal precioTotalBruto;
	BigDecimal precioTotalNeto;
	BigDecimal saldoPedido;
	BigDecimal pagoTotal;
	BigDecimal vueltoTotal;
	String esPagado;
	String tipoPedido;

}
