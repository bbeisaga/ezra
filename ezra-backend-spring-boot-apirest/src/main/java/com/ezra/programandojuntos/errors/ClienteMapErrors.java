package com.ezra.programandojuntos.errors;

import java.util.TreeMap;

import com.ezra.programandojuntos.dto.ErrorEzra;

public class ClienteMapErrors {
	private static TreeMap<String, ErrorEzra> errors; 
	//podria ser TreeMap, lo ordena por clave no importa el orden del put, pero tambien hashmap
	
	public static final String CODE_NUM_DOC_DUPLICADO = "C001";
	public static final String MSJ_NUM_DOC_DUPLICADO = "El documento %s ya esta registrado, puede recuperar la contraseña o comuniquese con el administrador";
	public static final String CODE_CEL_DUPLICADO = "C002";
	public static final String MSJ_CEL_DUPLICADO = "El cliente con celular %s ya esta registrado, puede recuperar la contraseña o comuniquese con el administrador";
	public static final String CODE_NO_CLIENTE_ID = "C003";
	public static final String MSJ_NO_CLIENTE_ID = "Cliente con Id: %g no existe";
	public static final String CODE_USERNAME_DUPLICADO = "C004";
	public static final String MSJ_USERNAME_DUPLICADO = "Usuario %g ya esta registrado";
	public static final String CODE_CLI_USR_DUPLICADO = "C005";
	public static final String MSJ_CLI_USR_DUPLICADO = "Cliente ya esta registrado, , puede recuperar la contraseña o comuniquese con el administrador";

	//bloque estaitco se jecuta una sola vez
	static {
		errors = new TreeMap<>();
		errors.put(CODE_NUM_DOC_DUPLICADO, new ErrorEzra(CODE_NUM_DOC_DUPLICADO, MSJ_NUM_DOC_DUPLICADO));
		errors.put(CODE_CEL_DUPLICADO, new ErrorEzra(CODE_CEL_DUPLICADO, MSJ_CEL_DUPLICADO));
		errors.put(CODE_NO_CLIENTE_ID, new ErrorEzra(CODE_NO_CLIENTE_ID, MSJ_NO_CLIENTE_ID));
		errors.put(CODE_USERNAME_DUPLICADO, new ErrorEzra(CODE_USERNAME_DUPLICADO, MSJ_USERNAME_DUPLICADO));
		errors.put(CODE_CLI_USR_DUPLICADO, new ErrorEzra(CODE_CLI_USR_DUPLICADO, MSJ_CLI_USR_DUPLICADO));

	}
	
	public static String getErrorString(String code, Object... args) {
		ErrorEzra errorEzra = errors.get(code);
		return String.format(errorEzra.getMensaje(), args);
	}

};
