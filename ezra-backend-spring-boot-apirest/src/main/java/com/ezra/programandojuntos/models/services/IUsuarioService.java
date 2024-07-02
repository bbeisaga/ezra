package com.ezra.programandojuntos.models.services;

import com.ezra.programandojuntos.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
