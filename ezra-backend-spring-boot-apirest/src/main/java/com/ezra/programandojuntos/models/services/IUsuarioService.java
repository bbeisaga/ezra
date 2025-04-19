package com.ezra.programandojuntos.models.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezra.programandojuntos.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
	
	public List<Usuario> getAllUsuarios();
	
	public Page<Usuario> findAllUsuarioPageable(String query, Pageable pageRequest);
	
	public Usuario updateRolUsuario(Usuario usuario, Long usuarioId) throws SQLException ;
	
	public void deleteRolUsuarioForGroup(Usuario usuario, Long usuarioId) throws SQLException ;
//	
//	public void createRolUsuario(Long usuarioId, Long rolId);
}
