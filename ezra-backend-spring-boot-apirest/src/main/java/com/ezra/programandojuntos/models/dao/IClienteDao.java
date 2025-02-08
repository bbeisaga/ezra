package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.TipoDocumento;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
 
//	@Query("from Region")
//	public List<Region> findAllRegiones();
	
	@Query("from TipoDocumento")
	public List<TipoDocumento> findAllDocumentos();
	
	
	@Query("SELECT c FROM Cliente c WHERE (c.nombres like %:query% OR c.apellidos like %:query% OR c.tipoDocumento.acronimo like %:query% OR c.numeroDocumento like %:query% OR c.celular like %:query%)")
	Page<Cliente> findAllClientePageable(@Param("query") String query, Pageable pageRequest);
}
