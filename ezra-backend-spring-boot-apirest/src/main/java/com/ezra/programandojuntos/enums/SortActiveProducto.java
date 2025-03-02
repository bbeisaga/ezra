package com.ezra.programandojuntos.enums;

public enum SortActiveProducto {
	NOMBRE("nombre");


	private String value;

	SortActiveProducto(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return String.valueOf(value);
	}
}
