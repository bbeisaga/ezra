package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezra.programandojuntos.dto.ParametrosPageable;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.TipoDocumento;
import com.ezra.programandojuntos.models.entity.Usuario;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAllClientePageable(String query, Pageable parametrosPageable) ;
	
//	public Page<Cliente> findAll(Pageable pageable);
	
	public Cliente findById(Long id);
	
	public Cliente findByNumeroDocumento(String numero);
	
	public Cliente findByNumeroCelular(String celular);
	
	public Cliente findClienteByUsuarioId(Long usuarioId);


	public Usuario findUsuarioByUsername(String username);
	
	public Cliente guardarCliente(Cliente cliente);
	
	public Cliente insertar(Cliente cliente);
	
	public Cliente actualizar(Cliente cliente, Long clienteId);

	public void delete(Long id);
	
	public List<Cliente> findClienteByNomApellRz (String term);
	
	//public List<Region> findAllRegiones();
	
	public List<TipoDocumento> findAllTipoDocumento();
	
//	public Pedido findPedidoById(Long id);
//	
//	public Pedido savePedido(Pedido pedido);
//	
//	public void deletePedidoById(Long id);
	
//	public List<Producto> findProductoByNombre(String term);

}
