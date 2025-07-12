package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ezra.programandojuntos.models.entity.ItemPedido;
import com.ezra.programandojuntos.models.entity.Pedido;

public interface ItemDao extends CrudRepository<ItemPedido, Long> {
	
	@Query("SELECT i FROM ItemPedido i WHERE i.producto.id = :productoId")
	List<ItemPedido> findItemsByProductoId(Long productoId);

}
