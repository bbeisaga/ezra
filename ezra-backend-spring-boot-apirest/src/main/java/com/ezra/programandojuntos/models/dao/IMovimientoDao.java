package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;
import com.ezra.programandojuntos.models.entity.TipoPago;

public interface IMovimientoDao extends JpaRepository<Movimiento, Long>{
 
	@Query("from TipoPago")
	public List<TipoPago> findAllTipoPagos();
	
	@Query("from TipoMovimiento")
	public List<TipoMovimiento> findAllTipoMovimientos();
}
