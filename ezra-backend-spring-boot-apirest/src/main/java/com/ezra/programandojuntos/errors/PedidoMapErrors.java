package com.ezra.programandojuntos.errors;

import java.util.TreeMap;

import com.ezra.programandojuntos.dto.ErrorEzra;

public class PedidoMapErrors {
	private static TreeMap<String, ErrorEzra> errors; 
	//podria ser TreeMap, lo ordena por clave no importa el orden del put, pero tambien hashmap
	
	public static final String CODE_FECHA_VENTA = "P001";
	public static final String MSJ_FECHA_VENTA = "Tiene que especificar fecha de entrega en la venta";

	public static final String CODE_FECHA_ADQUISICION = "P002";
	public static final String MSJ_FECHA_ADQUISICION = "Tiene que especificar fecha de adquisición en la compra";
	
	public static final String CODE_IMPORTE_ITEMS_PEDIDO = "P003";
	public static final String MSJ_IMPORTE_ITEMS_PEDIDO = "El importe para %s no es valido";

	//bloque estaitco se jecuta una sola vez
	static {
		errors = new TreeMap<>();
		errors.put(CODE_FECHA_VENTA, new ErrorEzra(CODE_FECHA_VENTA, MSJ_FECHA_VENTA));
		errors.put(CODE_FECHA_ADQUISICION, new ErrorEzra(CODE_FECHA_ADQUISICION, MSJ_FECHA_ADQUISICION));
		errors.put(CODE_IMPORTE_ITEMS_PEDIDO, new ErrorEzra(CODE_IMPORTE_ITEMS_PEDIDO, MSJ_IMPORTE_ITEMS_PEDIDO));

	}
	
	public static String getErrorString(String code, Object... args) {
		ErrorEzra errorEzra = errors.get(code);
		return String.format(errorEzra.getMensaje(), args);
	}

};
