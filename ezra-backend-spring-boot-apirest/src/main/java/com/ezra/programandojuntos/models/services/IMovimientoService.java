package com.ezra.programandojuntos.models.services;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.TipoMovimientoPedido;
import com.ezra.programandojuntos.models.entity.TipoPago;

public interface IMovimientoService {
	
	public List<TipoPago> lstAllTipoPagos();
	public List<TipoMovimientoPedido> lstAllTipoMovimientosPedido();	
	public Movimiento saveMovimiento(Movimiento movimiento);
	public ByteArrayInputStream createReportMovimientoEnCaja(ReportArray reporte);
	//public Map<String, BigDecimal> movimientoPorCajaUsuario(Long cajaUsuarioId);

}
