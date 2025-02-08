package com.ezra.programandojuntos.models.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.Producto;

public interface IPedidoService {
	
	public List<Pedido> findPedidoAll();
	
	public Page<Pedido> findAllPedidoPageable(String query, Pageable pagebale);
	
	//public Page<Cliente> findAll(Pageable pageable);
	
	//public Cliente findById(Long id);
	
	//public Cliente save(Cliente cliente);
	
	//public void delete(Long id);
	
	//public List<Region> findAllRegiones();
	
	public List<EstadoPedido> findAllEstadoPedido();
	
	public Pedido findPedidoById(Long id);
	
	public Pedido registrarPedido(Pedido pedido);
	
	public Pedido updatePedido(Pedido pedido, Long id);

	public void deletePedidoById(Long id);
	
	public List<Producto> findProductoByNombre(String term);
	
	public Map<String, BigDecimal> movimientoPorPedido(Long pedidoId);


}
