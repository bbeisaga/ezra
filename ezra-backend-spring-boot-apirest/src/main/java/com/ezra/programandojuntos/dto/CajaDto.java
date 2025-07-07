package com.ezra.programandojuntos.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CajaDto implements Serializable {

	private Byte id;
	private String nombre;
	private String ubicacion;

	private static final long serialVersionUID = 1L;

}
