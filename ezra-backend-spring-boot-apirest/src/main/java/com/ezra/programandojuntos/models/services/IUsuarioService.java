package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezra.programandojuntos.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
	
	public List<Usuario> getAllUsuarios();
	
	public Page<Usuario> findAllUsuarioPageable(String query, Pageable pageRequest);
}
