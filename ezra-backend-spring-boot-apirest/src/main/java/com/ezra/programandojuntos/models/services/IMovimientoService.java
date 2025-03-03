package com.ezra.programandojuntos.models.services;

import java.util.List;

import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;
import com.ezra.programandojuntos.models.entity.TipoPago;

public interface IMovimientoService {
	
	public List<TipoPago> lstAllTipoPagos();
	public List<TipoMovimiento> lstAllTipoMovimientos();	
	public Movimiento saveMovimiento(Movimiento movimiento);


}
