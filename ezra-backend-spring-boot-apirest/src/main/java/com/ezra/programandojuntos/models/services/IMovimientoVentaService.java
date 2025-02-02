package com.ezra.programandojuntos.models.services;

import java.util.List;

import com.ezra.programandojuntos.models.entity.MovimientoVenta;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;
import com.ezra.programandojuntos.models.entity.TipoPago;

public interface IMovimientoVentaService {
	
	public List<TipoPago> lstAllTipoPagos();
	public List<TipoMovimiento> lstAllTipoMovimientos();	
	public MovimientoVenta saveMovimiento(MovimientoVenta movimiento);


}
