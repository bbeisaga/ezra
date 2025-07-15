package com.ezra.programandojuntos.dto;

import lombok.Data;


/**
 * 
 */
@Data
public class EmailDto {
	private String emailDestino;
	private String asunto;
	private String saludo;
	private String mensaje;
	private String despedida;

}
