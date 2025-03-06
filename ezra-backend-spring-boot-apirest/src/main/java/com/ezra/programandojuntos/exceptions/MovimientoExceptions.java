package com.ezra.programandojuntos.exceptions;

public class MovimientoExceptions extends RuntimeException {

	protected String msj;

	public MovimientoExceptions() {
		super();
	}
	
	public MovimientoExceptions(String message) {
		super(message);
		this.msj = 	message;
	}
	
	public MovimientoExceptions(Throwable cause) {
		super(cause);
	}
	
	public MovimientoExceptions(String message, Throwable cause) {
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
