package com.ezra.programandojuntos.exceptions;

public class PedidoExceptions extends RuntimeException {

	//protected String msj;

	public PedidoExceptions() {
		super();
	}
	
	public PedidoExceptions(String message) {
		super(message);
		//this.msj = 	message;
	}
	
	public PedidoExceptions(Throwable cause) {
		super(cause);
	}
	
	public PedidoExceptions(String message, Throwable cause) {
		super(message, cause);
		//this.msj = 	message;
	}
	
//	public String getMsj() {
//		return this.msj;
//	}

//	public ClienteExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}

	private static final long serialVersionUID = 1L;

}
