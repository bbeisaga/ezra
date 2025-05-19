package com.ezra.programandojuntos.exceptions;

public class CajaUsuarioExceptions extends RuntimeException {

	protected String msj;

	public CajaUsuarioExceptions() {
		super();
	}
	
	public CajaUsuarioExceptions(String message) {
		super(message);
		this.msj = 	message;
	}
	
	public CajaUsuarioExceptions(Throwable cause) {
		super(cause);
	}
	
	public CajaUsuarioExceptions(String message, Throwable cause) {
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
