package com.ezra.programandojuntos.models.repository;

import java.util.List;

import com.ezra.programandojuntos.dto.PedidoReporte;
import com.ezra.programandojuntos.dto.report.Report;

public interface PedidoRepository {
	
	//public List<Pedido> listarPedidoConFiltros(Report reporte);
	
	public List<PedidoReporte> listarPedidosConFiltros(Report reporte);


}
