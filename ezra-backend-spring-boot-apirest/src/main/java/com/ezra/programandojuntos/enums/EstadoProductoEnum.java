package com.ezra.programandojuntos.enums;

public enum EstadoProductoEnum {
	NUEVO(1L), 
	EN_STOCK(2L),
	POR_AGOTARSE(3L), 
	AGOTADO(4L),
	PROXIMO(5L);

	private Long value;

	EstadoProductoEnum(Long value) {
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
