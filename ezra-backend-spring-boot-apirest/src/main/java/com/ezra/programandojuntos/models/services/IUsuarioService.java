package com.ezra.programandojuntos.models.services;

import java.util.List;

import com.ezra.programandojuntos.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
	
	public List<Usuario> getAllUsuarios();
}
