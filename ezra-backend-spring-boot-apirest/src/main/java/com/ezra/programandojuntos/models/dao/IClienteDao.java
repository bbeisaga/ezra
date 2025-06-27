package com.ezra.programandojuntos.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.TipoDocumento;
import com.ezra.programandojuntos.models.entity.Usuario;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
 
//	@Query("from Region")
//	public List<Region> findAllRegiones();
	
	@Query("from TipoDocumento")
	public List<TipoDocumento> findAllDocumentos();
	
	Optional<Cliente> findByNumeroDocumento(String numeroDocumento);
	
	@Query("SELECT c FROM Cliente c WHERE c.celular=:celular")
	Optional<Cliente> findByCelular(String celular);
	
	@Query("SELECT c FROM Cliente c WHERE c.usuarioId =:usuarioId")
	Optional<Cliente> findByUsuarioId(Long usuarioId);
	
	public List<Cliente> findByNomApellRzContainingIgnoreCase(String term);

	
	@Query("SELECT c FROM Cliente c WHERE (c.nomApellRz like %:query% OR c.numeroDocumento like %:query% OR c.celular like %:query%)")
	Page<Cliente> findAllClientePageable(@Param("query") String query, Pageable pageRequest);
	
	
	@Query("FROM Usuario u WHERE u.username =:username")
	Usuario findUsuarioByUsername(String username);
}
