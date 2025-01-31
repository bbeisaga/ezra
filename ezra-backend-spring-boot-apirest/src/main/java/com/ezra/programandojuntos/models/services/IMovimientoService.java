package com.ezra.programandojuntos.models.services;

import java.util.List;

import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;
import com.ezra.programandojuntos.models.entity.TipoPago;

public interface IMovimientoService {
	
	public List<TipoPago> lstAllTipoPagos();
	
	public List<TipoMovimiento> lstAllTipoMovimientos();

	
	public Movimiento saveMovimiento(Movimiento movimiento);

	
	//public Page<Cliente> findAll(Pageable pageable);
	
	//public Cliente findById(Long id);
	
	//public Cliente save(Cliente cliente);
	
	//public void delete(Long id);
	
	//public List<Region> findAllRegiones();
	
	//public List<TipoDocumento> findAllTipoDocumento();
	
//	public Pedido findPedidoById(Long id);
//	
//	public Pedido savePedido(Pedido pedido);
//	
//	public void deletePedidoById(Long id);
//	
//	public List<Producto> findProductoByNombre(String term);

}
