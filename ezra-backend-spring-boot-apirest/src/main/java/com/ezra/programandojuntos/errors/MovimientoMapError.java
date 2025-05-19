package com.ezra.programandojuntos.errors;

import java.util.TreeMap;

import com.ezra.programandojuntos.dto.ErrorEzra;

public class MovimientoMapError {
	
	private static TreeMap<String, ErrorEzra> errors; 
	//podria ser TreeMap, lo ordena por clave no importa el orden del put, pero tambien hashmap
	
	public static final String CODE_PAGO_DEL_CLIENTE = "M001";
	public static final String MSJ_PAGO_DEL_CLIENTE = "PAGO DEL CLIENTE no puede ser negativo";
	
	public static final String CODE_DEVOL_DINERO_CLIENTE = "M002";
	public static final String MSJ_DEVOL_DINERO_CLIENTE = "DEVOLUCIÓN DE DINERO AL CLIENTE no puede ser negativo";
	
	public static final String CODE_DEVOL_DINERO_CLIENTE_SIN_PAGO = "M003";
	public static final String MSJ_DEVOL_DINERO_CLIENTE_SIN_PAGO = "DEVOLUCIÓN DE DINERO AL CLIENTE no puede realizarse si no hay pago del cliente";

	public static final String CODE_COMPRA_AL_PROVEEDOR = "M004";
	public static final String MSJ_COMPRA_AL_PROVEEDOR = "COMPRA DE MATERIAL O INSUMOS AL PROVEEDOR no puede ser negativo";
	
	public static final String CODE_DEVOL_COMPRA = "M005";
	public static final String MSJ_DEVOL_COMPRA = "DEVOLUCION COMPRA no puede ser negativo";
	
	public static final String CODE_DEVOL_COMPRA_PROVEEDOR_SIN_PAGO = "M006";
	public static final String MSJ_DEVOL_PROVEEDOR_SIN_PAGO = "DEVOLUCION COMPRA no puede realizarse si no hay pago al proveedor";
	
	public static final String CODE_SELECCIONAR_TIPO_PAGO = "M007";
	public static final String MSJ_SELECCIONAR_TIPO_PAGO = "Tiene que seleccionar un TIPO DE PAGO";
	
	public static final String CODE_SALDO_INSUFICIENTE_CJU = "M008";
	public static final String MSJ_SALDO_INSUFICIENTE_CJU = "SALDO INSUFICIENTE en caja";
	
	public static final String CODE_COMPRA_SIN_SALDO = "M009";
	public static final String MSJ_COMPRA_SIN_SALDO = "COMPRA DE MATERIAL O INSUMOS AL PROVEEDOR no puede realizarse si no hay saldo";
	
	//bloque estaitco se jecuta una sola vez
	static {
		errors = new TreeMap<>();
		errors.put(CODE_PAGO_DEL_CLIENTE, new ErrorEzra(CODE_PAGO_DEL_CLIENTE, MSJ_PAGO_DEL_CLIENTE));
		errors.put(CODE_DEVOL_DINERO_CLIENTE, new ErrorEzra(CODE_DEVOL_DINERO_CLIENTE, MSJ_DEVOL_DINERO_CLIENTE));
		errors.put(CODE_DEVOL_DINERO_CLIENTE_SIN_PAGO, new ErrorEzra(CODE_DEVOL_DINERO_CLIENTE_SIN_PAGO, MSJ_DEVOL_DINERO_CLIENTE_SIN_PAGO));
		errors.put(CODE_COMPRA_AL_PROVEEDOR, new ErrorEzra(CODE_COMPRA_AL_PROVEEDOR, MSJ_COMPRA_AL_PROVEEDOR));
		errors.put(CODE_DEVOL_COMPRA, new ErrorEzra(CODE_DEVOL_COMPRA, MSJ_DEVOL_COMPRA));
		errors.put(CODE_DEVOL_COMPRA_PROVEEDOR_SIN_PAGO, new ErrorEzra(CODE_DEVOL_COMPRA_PROVEEDOR_SIN_PAGO, MSJ_DEVOL_PROVEEDOR_SIN_PAGO));	
		errors.put(CODE_SELECCIONAR_TIPO_PAGO, new ErrorEzra(CODE_SELECCIONAR_TIPO_PAGO, MSJ_SELECCIONAR_TIPO_PAGO));
		errors.put(CODE_SALDO_INSUFICIENTE_CJU, new ErrorEzra(CODE_SALDO_INSUFICIENTE_CJU, MSJ_SALDO_INSUFICIENTE_CJU));

	}
	
	public static String getErrorString(String code, Object... args) {
		ErrorEzra errorEzra = errors.get(code);
		return String.format(errorEzra.getMensaje(), args);
	}

}
