package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.Pedido;

public interface IPedidoDao extends JpaRepository<Pedido, Long>{
	
	@Query("from EstadoPedido")
	public List<EstadoPedido> findAllEstadoPedido();

}
