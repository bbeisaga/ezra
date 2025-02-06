package com.ezra.programandojuntos.models.services;

import java.util.List;

import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;

public interface IMovimientoCajaService {
	
	public List<TipoMovimiento> lstAllTipoMovimientos();	
	public MovimientoCaja saveMovimiento(MovimientoCaja movimiento);


}
