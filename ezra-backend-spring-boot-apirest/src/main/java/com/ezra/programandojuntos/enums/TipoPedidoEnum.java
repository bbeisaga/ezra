package com.ezra.programandojuntos.enums;

public enum TipoPedidoEnum {
	VENTA(1L), 
	COMPRA(2L);

	private Long value;

	TipoPedidoEnum(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
