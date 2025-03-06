package com.ezra.programandojuntos.errors;

import java.util.TreeMap;

import com.ezra.programandojuntos.dto.ErrorEzra;

public class ClienteMapErrors {
	private static TreeMap<String, ErrorEzra> errors; 
	//podria ser TreeMap, lo ordena por clave no importa el orden del put, pero tambien hashmap
	
	public static final String CODE_NUM_DOC_DUPLICADO = "C001";
	public static final String MSJ_NUM_DOC_DUPLICADO = "El documento %s ya esta registrado";
	public static final String CODE_CEL_DUPLICADO = "C002";
	public static final String MSJ_CEL_DUPLICADO = "El celular %s ya esta registrado";
	public static final String CODE_NO_CLIENTE_ID = "C003";
	public static final String MSJ_NO_CLIENTE_ID = "Cliente con Id: %g no existe";

	//bloque estaitco se jecuta una sola vez
	static {
		errors = new TreeMap<>();
		errors.put(CODE_NUM_DOC_DUPLICADO, new ErrorEzra(CODE_NUM_DOC_DUPLICADO, MSJ_NUM_DOC_DUPLICADO));
		errors.put(CODE_CEL_DUPLICADO, new ErrorEzra(CODE_CEL_DUPLICADO, MSJ_CEL_DUPLICADO));

	}
	
	public static String getErrorString(String code, Object... args) {
		ErrorEzra errorEzra = errors.get(code);
		return String.format(errorEzra.getMensaje(), args);
	}

};
