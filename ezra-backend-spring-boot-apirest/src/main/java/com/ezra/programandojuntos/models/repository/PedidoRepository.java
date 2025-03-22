package com.ezra.programandojuntos.models.repository;

import java.util.List;

import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.models.entity.Pedido;

public interface PedidoRepository {
	
	//public List<Pedido> listarPedidoConFiltros(Report reporte);
	
	public List<Pedido> listarPedidoConFiltros(Report reporte);


}
