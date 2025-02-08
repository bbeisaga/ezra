package com.ezra.programandojuntos.enums;

public enum SortActivePedido {
	NOMBRES("cliente.nombres"), 
	APELLIDOS("cliente.apellidos"),
	CREATEAT("createAt"),
	ENTREGADOEN("entregadoEn"),
	SALDOPEDIDO("saldoPedido"),
	ESTADO("estado");

	private String value;

	SortActivePedido(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return String.valueOf(value);
	}
}
