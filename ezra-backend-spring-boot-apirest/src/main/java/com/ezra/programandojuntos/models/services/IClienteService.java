package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.TipoDocumento;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	//public List<Region> findAllRegiones();
	
	public List<TipoDocumento> findAllTipoDocumento();
	
//	public Pedido findPedidoById(Long id);
//	
//	public Pedido savePedido(Pedido pedido);
//	
//	public void deletePedidoById(Long id);
	
//	public List<Producto> findProductoByNombre(String term);

}
