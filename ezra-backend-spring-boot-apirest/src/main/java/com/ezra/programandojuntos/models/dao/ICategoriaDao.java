package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ezra.programandojuntos.models.entity.Categoria;
import com.ezra.programandojuntos.models.entity.Producto;

public interface ICategoriaDao extends CrudRepository<Categoria, Long> {
	@Query("SELECT c FROM Categoria c WHERE c.activa=true")
	List<Categoria> listCategoryActive();

}
