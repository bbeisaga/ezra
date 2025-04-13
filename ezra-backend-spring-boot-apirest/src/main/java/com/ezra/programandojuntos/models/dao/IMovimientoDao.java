package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.TipoMovimientoPedido;
import com.ezra.programandojuntos.models.entity.TipoPago;

public interface IMovimientoDao extends JpaRepository<Movimiento, Long>{
 
	@Query("from TipoPago")
	public List<TipoPago> findAllTipoPagos();
	
	@Query("from TipoMovimientoPedido")
	public List<TipoMovimientoPedido> findAllTipoMovimientosPedido();
}
