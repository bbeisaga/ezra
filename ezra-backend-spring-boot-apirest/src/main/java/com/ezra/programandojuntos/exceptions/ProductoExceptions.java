package com.ezra.programandojuntos.exceptions;

public class ProductoExceptions extends RuntimeException {

	protected String msj;

	public ProductoExceptions() {
		super();
	}
	
	public ProductoExceptions(String message) {
		super(message);
		this.msj = 	message;
	}
	
	public ProductoExceptions(Throwable cause) {
		super(cause);
	}
	
	public ProductoExceptions(String message, Throwable cause) {
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
