package com.ezra.programandojuntos.models.services;

import java.util.List;

import com.ezra.programandojuntos.models.entity.Categoria;

public interface ICategoriaService {
	public Categoria createCategory(Categoria Categoria);
	public Categoria updateCategory(Categoria Categoria, Long id);
	public List<Categoria> listCategoryActive();
}
