package com.ezra.programandojuntos.models.repository;

import java.util.List;

import com.ezra.programandojuntos.dto.MovimientoEnCajaReporte;
import com.ezra.programandojuntos.dto.report.ReportArray;

public interface MovimientoRepository {

	
	public List<MovimientoEnCajaReporte> listarMovimientoEnCajaConFiltros(ReportArray reporte);

}
