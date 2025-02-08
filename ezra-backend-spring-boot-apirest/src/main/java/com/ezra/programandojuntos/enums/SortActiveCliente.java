package com.ezra.programandojuntos.enums;

public enum SortActiveCliente {
	NOMBRES("nombres"), 
	APELLIDOS("apellidos"),
	CREATEAT("createAt");

	private String value;

	SortActiveCliente(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return String.valueOf(value);
	}
}
