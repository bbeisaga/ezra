package com.ezra.programandojuntos.models.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.IPedidoDao;
import com.ezra.programandojuntos.models.dao.IProductoDao;
import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.ItemPedido;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.Producto;

@Service
public class PedidoServiceImpl implements IPedidoService {
	
	/* Estados del pedido 
			Pagado	entrega(vencido)		aceptado
Registrado	-		no						no
Vencido		-		si						no
Devuelto 	-		si						no
Entregado	si		-						si*/
	
	public static final Byte PEDIDO_REGISTRADO = 1;
	public static final Byte PEDIDO_VENCIDO = 2;
	public static final Byte PEDIDO_DEVUELTO = 3;
	public static final Byte PEDIDO_ENTREGADO = 4;

	@Autowired
	private IPedidoDao pedidoDao;
	
	@Autowired
	private IProductoDao productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Pedido> findPedidoAll() {
		return (List<Pedido>) pedidoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido findPedidoById(Long id) {
		return pedidoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Pedido savePedido(Pedido pedido) {
				/* Estados del pedido 
				Pagado	entrega(vencido)		aceptado
		Registrado	-		no						no
		Vencido		-		si						no
		Devuelto 	-		si						no
		Entregado	si	*/
		Long idTrae = pedido.getId();
		if (idTrae!=null) {
			Pedido pedidoActual = findPedidoById(pedido.getId());
			if(pedidoActual.getEntregadoEn().before(new Date())) {
				pedidoActual.setVencido(true);
			}
			pedidoActual.setTotal(pendientePago(pedidoActual.getItems(), 0d));
			pedidoActual.setPago((pedidoActual.getPago() + pedido.getApagar()));
			pedidoActual.setSaldo(pendientePago(pedidoActual.getItems(), pedidoActual.getPago()));
			if(pedidoActual.getSaldo() < 1) {
				pedidoActual.setPagado(true);
			}
			pedidoActual.setAceptado(pedido.isAceptado());
			pedidoActual.setEstadoPedido(new EstadoPedido());
			pedidoActual.getEstadoPedido().setId(pedido.getEstadoPedido().getId());

			if(pedidoActual.isVencido() && !pedido.isAceptado()) {
				pedidoActual.getEstadoPedido().setId(PEDIDO_VENCIDO);
			}  
			
			if(pedidoActual.isPagado() && pedido.isAceptado()) {
					pedidoActual.setAceptado(true);
					pedidoActual.setEntregadoEn(new Date());
					pedidoActual.getEstadoPedido().setId(PEDIDO_ENTREGADO);
			} 
			pedidoActual.setApagar(0d);
			return pedidoDao.save(pedidoActual);
				
		} else {
		
			if(pedido.getEntregadoEn().before(new Date())) {
				pedido.setVencido(true);
			}
			pedido.setTotal(pendientePago(pedido.getItems(), 0d));
			pedido.setPago((pedido.getPago()));
			pedido.setSaldo(pendientePago(pedido.getItems(), pedido.getPago()));
			if(pedido.getSaldo() < 1) {
				pedido.setPagado(true);
			}
			pedido.setEstadoPedido(new EstadoPedido());
			if(pedido.isVencido() && !pedido.isAceptado()) {
				pedido.getEstadoPedido().setId(PEDIDO_VENCIDO);
			} else {
				pedido.getEstadoPedido().setId(PEDIDO_REGISTRADO);
			}
			pedido.setTotal(pendientePago(pedido.getItems(), 0D));
			pedido.setSaldo(pendientePago(pedido.getItems(), pedido.getPago()));
			pedido.setAceptado(false);
			pedido.setApagar(0d);
			return pedidoDao.save(pedido);
		}
	
	}
	
	public Double pendientePago (List<ItemPedido> itemPedido, Double pago) {
		//List<ItemPedido> items= pedido.getItems();
		Double total = 0.00;
		for (ItemPedido item : itemPedido) {
			total += item.getImporte(); // esto incluye la cantidad por el precio
		}
		return (total - pago);
	}

	@Override
	@Transactional
	public void deletePedidoById(Long id) {
		pedidoDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(String term) {
		return productoDao.findByNombreContainingIgnoreCase(term);
	}
	
	
	


}
