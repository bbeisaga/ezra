package com.ezra.programandojuntos.models.repository;

import java.util.List;

import com.ezra.programandojuntos.dto.CajaDto;

public interface CajaRepository {
	
	//public List<Pedido> listarPedidoConFiltros(Report reporte);
	
	public List<CajaDto> listarCajasPorAsignar();


}
