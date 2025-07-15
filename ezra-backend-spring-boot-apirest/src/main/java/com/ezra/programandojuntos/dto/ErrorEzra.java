package com.ezra.programandojuntos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorEzra {
	private String code;
	private String msj;

	
	public String getMensajeWithCode() {
		return this.code.concat(": ").concat(this.msj);
	}
	
	public String getMensaje() {
		return this.msj;
	}

	public ErrorEzra() {
		super();
	}

	public ErrorEzra(String code, String msj) {
		super();
		this.code = code;
		this.msj = msj;
	}
	
	
}
