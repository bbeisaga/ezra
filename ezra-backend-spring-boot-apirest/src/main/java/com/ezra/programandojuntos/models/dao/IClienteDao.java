package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.TipoDocumento;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
 
//	@Query("from Region")
//	public List<Region> findAllRegiones();
	
	@Query("from TipoDocumento")
	public List<TipoDocumento> findAllDocumentos();
}
