package com.ezra.programandojuntos.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CajaUsuarioReporte {
	String nombreCaja;
	String apellidoUsuario;
	String nombreUsuario;
	String fechaApertura;
	String fechaActualizacion;
	String fechaCierre;
	BigDecimal ingresoEsperado;
	BigDecimal egresoEsperado;
	BigDecimal saldoCaja;
	BigDecimal saldoPorConteo;
}
