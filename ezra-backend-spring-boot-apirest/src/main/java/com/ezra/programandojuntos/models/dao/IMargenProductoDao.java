package com.ezra.programandojuntos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ezra.programandojuntos.models.entity.MargenProducto;
import com.ezra.programandojuntos.models.entity.Producto;

public interface IMargenProductoDao extends CrudRepository<MargenProducto, Long>{
	
}
