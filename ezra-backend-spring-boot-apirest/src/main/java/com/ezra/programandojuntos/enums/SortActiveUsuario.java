package com.ezra.programandojuntos.enums;

public enum SortActiveUsuario {
	NOMBRES("nombres"), 
	APELLIDOS("apellidos");

	private String value;

	SortActiveUsuario(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return String.valueOf(value);
	}
}
