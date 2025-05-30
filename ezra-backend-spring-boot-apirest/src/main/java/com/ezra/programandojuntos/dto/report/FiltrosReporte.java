package com.ezra.programandojuntos.dto.report;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FiltrosReporte {
	private String nombre;
	private Long tipoPedido;
//	@NotBlank
//    private String nombreArchivo;
	@NotBlank
	private String tipo = "EXCELOPENXML";
//	private Object filtros;
	private Map<String, String> filtros;
}
