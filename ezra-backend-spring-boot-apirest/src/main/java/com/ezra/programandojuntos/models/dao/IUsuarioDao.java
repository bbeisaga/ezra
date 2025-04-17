package com.ezra.programandojuntos.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String username);
	
	@Query("SELECT u FROM Usuario u WHERE (u.apellidos like %:query% OR u.nombres like %:query%)")
	Page<Usuario> findAllUsuarioPageable(@Param("query") String query, Pageable pageRequest);

}
