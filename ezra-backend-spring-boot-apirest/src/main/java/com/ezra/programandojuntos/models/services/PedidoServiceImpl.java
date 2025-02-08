package com.ezra.programandojuntos.models.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.IPedidoDao;
import com.ezra.programandojuntos.models.dao.IProductoDao;
import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.ItemPedido;
import com.ezra.programandojuntos.models.entity.MovimientoVenta;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.Producto;

@Service
public class PedidoServiceImpl implements IPedidoService {
	
	Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);
	
	/* Estados del pedido 
			Pagado	entrega(vencido)		aceptado
Registrado	-		no						no
Vencido		-		si						no
Devuelto 	-		si						no
Entregado	si		-						si*/
	
	public static final Long PEDIDO_REGISTRADO = 1L;
	public static final Long PEDIDO_VENCIDO = 2L;
	public static final Long PEDIDO_DEVUELTO = 3L;
	public static final Long PEDIDO_ENTREGADO = 4L;

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
	public Page<Pedido> findAllPedidoPageable(String query, Pageable pageable) {
		
		log.info("findAllPedidoPageable pageable= {}",pageable);
	
		return pedidoDao.findAllPedidoPageable(query, pageable);
	}
	

	@Override
	@Transactional(readOnly = true)
	public Pedido findPedidoById(Long id) {
		return pedidoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Pedido registrarPedido(Pedido pedido) {		
		pedido.setPagado(false);
		pedido.setAceptado(false);
		pedido.setVencido(false);
		if(pedido.getEntregadoEn().before(new Date())) {
			pedido.setVencido(true);
		}
		pedido.setPrecioBrutoTotal(new BigDecimal(0));
		pedido.setPagoBrutoTotal(new BigDecimal(0));
		pedido.setPagoNetoTotal(new BigDecimal(0));
		pedido.setPrecioNetoTotal(pendientePago(pedido.getItems(), pedido.getPagoNetoTotal()));
		pedido.setSaldoPedido(pedido.getPrecioNetoTotal());
		if(pedido.getSaldoPedido().intValue() < 1) {
			pedido.setPagado(true);
		}
//		pedido.setEstadoPedido(new EstadoPedido());
//		if(pedido.isVencido() && !pedido.isAceptado()) {
//			pedido.getEstadoPedido()
//			.setId(PEDIDO_VENCIDO);
//		} else {
//			pedido.getEstadoPedido()
//			.setId(PEDIDO_REGISTRADO);
//		}
		
		if(pedido.isVencido() && !pedido.isAceptado()) {
			pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido()
					.stream()
					.filter(estado -> estado.getId()== PEDIDO_VENCIDO)
					.collect(Collectors.toList())
					.get(0));
		} else {
			pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido()
					.stream()
					.filter(estado -> estado.getId()== PEDIDO_REGISTRADO)
					.collect(Collectors.toList())
					.get(0));

		}
		return pedidoDao.save(pedido);
	}
	
	@Override
	@Transactional
	public Pedido updatePedido(Pedido pedido, Long id) {	
		Pedido pedidoActual = findPedidoById(id);
		List<EstadoPedido> lstEstadosPedido = findAllEstadoPedido();
		if (pedidoActual!=null) {
			if(pedidoActual.getEntregadoEn().before(new Date())) {
				pedidoActual.setVencido(true);
			}
			pedidoActual.setPrecioNetoTotal(pendientePago(pedidoActual.getItems(), new BigDecimal(0)));
			pedidoActual.setPagoBrutoTotal(new BigDecimal(0));
			
			Map<String, BigDecimal> movimientosPedido = movimientoPorPedido(id);

			//pedidoActual.setPagoNetoTotal(ingresoDineroPorPedido(pedidoActual.getMovimientos(),  new BigDecimal(0)));
			
			pedidoActual.setPagoNetoTotal(movimientosPedido.get("ingresoPedido"));
			pedidoActual.setVueltoNetoTotal(movimientosPedido.get("egresoPedido"));
			pedidoActual.setSaldoPedido(pendientePago(pedidoActual.getItems(), movimientosPedido.get("saldoPedido")));
			if(pedidoActual.getSaldoPedido().intValue() < 1) {
				pedidoActual.setPagado(true);
			}
			
			pedidoActual.setAceptado(pedido.isAceptado());
			//pedidoActual.getEstadoPedido().setId(PEDIDO_REGISTRADO);
			pedidoActual.setEstadoPedido(
					lstEstadosPedido.stream()
					.filter(e->e.getId()==PEDIDO_REGISTRADO)
					.collect(Collectors.toList())
					.get(0));

			if(pedidoActual.isVencido() && !pedido.isAceptado()) {
				pedidoActual.setEstadoPedido(
						lstEstadosPedido.stream()
						.filter(e->e.getId()==PEDIDO_VENCIDO)
						.collect(Collectors.toList())
						.get(0));			}  
			
			if(pedidoActual.isPagado() && pedido.isAceptado()) {
					pedidoActual.setAceptado(true);
					pedidoActual.setEntregadoEn(new Date());
					pedidoActual.setEstadoPedido(
							lstEstadosPedido.stream()
							.filter(e->e.getId()==PEDIDO_ENTREGADO)
							.collect(Collectors.toList())
							.get(0));			} 
			
		} 
		return pedidoDao.save(pedidoActual);
	}
	
	
	public BigDecimal pendientePago (List<ItemPedido> itemPedido, BigDecimal pago) {
		BigDecimal total = new BigDecimal(0);
		for (ItemPedido item : itemPedido) {
			total = total.add(item.getImporte());
		}
		return (total.subtract(pago));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, BigDecimal> movimientoPorPedido(Long pedidoId){
		Pedido pedido = findPedidoById(pedidoId);
		List<MovimientoVenta> items= pedido.getMovimientosVenta();
		BigDecimal saldoPedido = new BigDecimal(0);
		BigDecimal ingresoPedido = new BigDecimal(0);
		BigDecimal egresoPedido = new BigDecimal(0);

		for (MovimientoVenta item : items) {
			ingresoPedido = ingresoPedido.add(item.getIngresoDinero()); //+
			egresoPedido = egresoPedido.add(item.getEgresoDinero()); // -
		}
		saldoPedido = ingresoPedido.add(egresoPedido);
        Map<String, BigDecimal> mapa = new HashMap<String, BigDecimal>();
        mapa.put("ingresoPedido", ingresoPedido);
        mapa.put("egresoPedido", egresoPedido);
        mapa.put("saldoPedido", saldoPedido);
		return mapa;
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
	
	@Override
	@Transactional(readOnly = true)
	public List<EstadoPedido> findAllEstadoPedido(){
		return pedidoDao.findAllEstadoPedido();
	}

	
	
	


}
