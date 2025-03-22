package com.ezra.programandojuntos.exceptions;

public class StorageExceptions extends RuntimeException {

	protected String msj;

	public StorageExceptions() {
		super();
	}
	
	public StorageExceptions(String message) {
		super(message);
		this.msj = 	message;
	}
	
	public StorageExceptions(Throwable cause) {
		super(cause);
	}
	
	public StorageExceptions(String message, Throwable cause) {
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
