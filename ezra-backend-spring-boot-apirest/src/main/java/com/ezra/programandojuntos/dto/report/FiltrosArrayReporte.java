package com.ezra.programandojuntos.dto.report;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FiltrosArrayReporte {
	@NotBlank
    private String nombre;
	@NotBlank
    private String tipo = "EXCELOPENXML";
    private Map<String, List<String>> filtros;
}
