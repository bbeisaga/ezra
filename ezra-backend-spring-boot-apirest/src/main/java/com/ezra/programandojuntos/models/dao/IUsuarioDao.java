package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String username);
	
	@Query("SELECT u FROM Usuario u WHERE (u.apellidos like %:query% OR u.nombres like %:query%)")
	Page<Usuario> findAllUsuarioPageable(@Param("query") String query, Pageable pageRequest);

	@Transactional
	@Modifying
	@Query (value = "DELETE FROM usuarios_roles WHERE usuario_id = :usuarioId AND role_id IN (:idsInsert)" ,nativeQuery = true)
	void deleteRolUsuario(Long usuarioId, List<Long> idsInsert);
	
//	@Query (value = "DELETE FROM Usuario. u WHERE u.usuarioId = ?1 AND ur.rol_id = ?2" ,nativeQuery = true)
//	void deleteRolUsuario(Long userId, Byte cajaId);
	
//	@Transactional
//	@Modifying
//	@Query (value = "INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (?1, ?2);" ,nativeQuery = true)
//	void persistRolUsuario(Long usuarioId, List<Long> idsInsert);
}
