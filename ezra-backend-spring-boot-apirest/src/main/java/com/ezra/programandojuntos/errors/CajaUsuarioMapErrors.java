package com.ezra.programandojuntos.errors;

import java.util.TreeMap;

import com.ezra.programandojuntos.dto.ErrorEzra;

public class CajaUsuarioMapErrors {
	private static TreeMap<String, ErrorEzra> errors; 
	//podria ser TreeMap, lo ordena por clave no importa el orden del put, pero tambien hashmap
	
	public static final String CODE_SALDO_INSUFICIENTE = "CJU001";
	public static final String MSJ_SALDO_INSUFICIENTE = "Saldo insuficiente en caja";

	static {
		errors = new TreeMap<>();
		errors.put(CODE_SALDO_INSUFICIENTE, new ErrorEzra(CODE_SALDO_INSUFICIENTE, MSJ_SALDO_INSUFICIENTE));
	}
	
	public static String getErrorString(String code, Object... args) {
		ErrorEzra errorEzra = errors.get(code);
		return String.format(errorEzra.getMensaje(), args);
	}

};
