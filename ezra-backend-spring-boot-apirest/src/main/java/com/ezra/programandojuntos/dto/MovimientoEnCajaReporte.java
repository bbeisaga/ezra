package com.ezra.programandojuntos.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimientoEnCajaReporte {
	public String caja;
	public String usuario;
	public Long codPedido;
	public String clienteProveedor;
	public String tipoDocumento;
	public String numDocumento;
	public String fechaTransaccion;
	public BigDecimal ingreso;
	public BigDecimal egreso;
	public String tipoMovimiento;
	public String movimiento;
	public String modulo;


}
