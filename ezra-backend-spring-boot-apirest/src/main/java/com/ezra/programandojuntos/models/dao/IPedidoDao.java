package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.Pedido;

public interface IPedidoDao extends JpaRepository<Pedido, Long>{
	
	@Query("from EstadoPedido")
	public List<EstadoPedido> findAllEstadoPedido();
	
	@Query("SELECT p FROM Pedido p WHERE (p.cliente.nombres like %:query% OR p.cliente.apellidos like %:query% OR p.estadoPedido.estado like %:query%)")
	Page<Pedido> findAllPedidoPageable(@Param("query") String query, Pageable pageable);

}
