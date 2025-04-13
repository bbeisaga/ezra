package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.entity.TipoMovimientoCaja;

public interface IMovimientoCajaDao extends JpaRepository<MovimientoCaja, Long>{
 	
	@Query("from TipoMovimientoCaja")
	public List<TipoMovimientoCaja> findAllTipoMovimientosCaja();
}
