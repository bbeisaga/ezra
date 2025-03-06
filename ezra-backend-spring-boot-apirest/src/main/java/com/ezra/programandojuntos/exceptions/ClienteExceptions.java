package com.ezra.programandojuntos.exceptions;

public class ClienteExceptions extends RuntimeException {

	protected String msj;

	public ClienteExceptions() {
		super();
	}
	
	public ClienteExceptions(String message) {
		super(message);
		this.msj = 	message;
	}
	
	public ClienteExceptions(Throwable cause) {
		super(cause);
	}
	
	public ClienteExceptions(String message, Throwable cause) {
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
