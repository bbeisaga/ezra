package com.ezra.programandojuntos.exceptions;

public class MovimientoCajaExceptions extends RuntimeException {

	protected String msj;

	public MovimientoCajaExceptions() {
		super();
	}
	
	public MovimientoCajaExceptions(String message) {
		super(message);
		this.msj = 	message;
	}
	
	public MovimientoCajaExceptions(Throwable cause) {
		super(cause);
	}
	
	public MovimientoCajaExceptions(String message, Throwable cause) {
		super(message, cause);
		this.msj = 	message;
	}
	
	public String getMsj() {
		return this.msj;
	}

//	public ClienteExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}

	private static final long serialVersionUID = 1L;

}
