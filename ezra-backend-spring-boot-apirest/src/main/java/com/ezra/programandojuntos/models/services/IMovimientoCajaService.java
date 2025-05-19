package com.ezra.programandojuntos.models.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ezra.programandojuntos.exceptions.CajaUsuarioExceptions;
import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.entity.TipoMovimientoCaja;

public interface IMovimientoCajaService {
	
	public List<TipoMovimientoCaja> lstAllTipoMovimientosCaja();	
	public MovimientoCaja saveMovimiento(MovimientoCaja movimiento) throws CajaUsuarioExceptions;


}
