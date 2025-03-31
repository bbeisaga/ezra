package com.ezra.programandojuntos.models.repository;

import java.util.List;

import com.ezra.programandojuntos.dto.CajaUsuarioReporte;
import com.ezra.programandojuntos.dto.report.ReportArray;

public interface CajaUsuarioRepository {
	
	//public List<Pedido> listarPedidoConFiltros(Report reporte);
	
	public List<CajaUsuarioReporte> listarCajaUsuarioConFiltros(ReportArray reporte);


}
