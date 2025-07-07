package com.ezra.programandojuntos.errors;

import java.util.TreeMap;

import com.ezra.programandojuntos.dto.ErrorEzra;

public class PedidoMapErrors {
	private static TreeMap<String, ErrorEzra> errors;
	// podria ser TreeMap, lo ordena por clave no importa el orden del put, pero
	// tambien hashmap

	public static final String CODE_FECHA_VENTA = "P001";
	public static final String MSJ_FECHA_VENTA = "Tiene que especificar fecha de entrega en la venta";

	public static final String CODE_FECHA_ADQUISICION = "P002";
	public static final String MSJ_FECHA_ADQUISICION = "Tiene que especificar fecha de adquisición en la compra";

	public static final String CODE_IMPORTE_ITEMS_PEDIDO = "P003";
	public static final String MSJ_IMPORTE_ITEMS_PEDIDO = "El importe para %s no es valido";

	public static final String CODE_STOCK_INSUFICIENTE = "P004";
	public static final String MSJ_STOCK_INSUFICIENTE = "No hay stock suficiente para atender %d unidades de %s.";
	
	public static final String CODE_SIN_MARGEN_GANANCIA = "P005";
	public static final String MSJ_SIN_MARGEN_GANANCIA = " %s no tiene margen de ganancia configurado";
	
	public static final String CODE_SIN_IGV = "P006";
	public static final String MSJ_SIN_IGV = " %s no tiene IGV configurado";
	
	public static final String CODE_FECHA_ADQUISICION_FUTURA = "P007";
	public static final String MSJ_FECHA_ADQUISICION_FUTURA = "La fecha del pedido puede ser futura, por actualización de stocks";
	
	public static final String CODE_FECHA_VENTA_PASADA = "P008";
	public static final String MSJ_FECHA_VENTA_PASADA = "La fecha del pedido no puede ser pasada, por actualización de stocks";

	// bloque estaitco se jecuta una sola vez
	static {
		errors = new TreeMap<>();
		errors.put(CODE_FECHA_VENTA, new ErrorEzra(CODE_FECHA_VENTA, MSJ_FECHA_VENTA));
		errors.put(CODE_FECHA_ADQUISICION, new ErrorEzra(CODE_FECHA_ADQUISICION, MSJ_FECHA_ADQUISICION));
		errors.put(CODE_IMPORTE_ITEMS_PEDIDO, new ErrorEzra(CODE_IMPORTE_ITEMS_PEDIDO, MSJ_IMPORTE_ITEMS_PEDIDO));
		errors.put(CODE_STOCK_INSUFICIENTE, new ErrorEzra(CODE_STOCK_INSUFICIENTE, MSJ_STOCK_INSUFICIENTE));
		errors.put(CODE_SIN_MARGEN_GANANCIA, new ErrorEzra(CODE_SIN_MARGEN_GANANCIA, MSJ_SIN_MARGEN_GANANCIA));
		errors.put(CODE_SIN_IGV, new ErrorEzra(CODE_SIN_IGV, MSJ_SIN_IGV));
		errors.put(CODE_FECHA_ADQUISICION_FUTURA, new ErrorEzra(CODE_FECHA_ADQUISICION_FUTURA, MSJ_FECHA_ADQUISICION_FUTURA));
		errors.put(CODE_FECHA_VENTA_PASADA, new ErrorEzra(CODE_FECHA_VENTA_PASADA, MSJ_FECHA_VENTA_PASADA));
	}

	public static String getErrorString(String code, Object... args) {
		ErrorEzra errorEzra = errors.get(code);
		return String.format(errorEzra.getMensaje(), args);
	}

};
