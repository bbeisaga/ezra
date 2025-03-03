package com.ezra.programandojuntos.models.services;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.IMovimientoDao;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;
import com.ezra.programandojuntos.models.entity.TipoPago;

@Service
public class MovimientoServiceImpl implements IMovimientoService {
	
	Logger log = LoggerFactory.getLogger(MovimientoServiceImpl.class);
	
	@Autowired
	IMovimientoDao movimientoDao;
	
//	@Autowired
//	IPedidoDao pedidoDao;
	
	@Autowired
	IPedidoService pedidoService;
	
	@Autowired
	ICajaUsuarioService cajaUsuarioService;
	
//	
//	@Autowired
//	ICajaUsuarioDao cajaUsuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<TipoPago> lstAllTipoPagos() {
		return movimientoDao.findAllTipoPagos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoMovimiento> lstAllTipoMovimientos(){
		return movimientoDao.findAllTipoMovimientos();
	}
	
	@Override
	@Transactional
	public Movimiento saveMovimiento (Movimiento movimiento){
		Movimiento newMovimiento = null;
		CajaUsuario cajaUsuario = cajaUsuarioService
				.findCajaUsuarioByUserIdAndCajaId(movimiento.getCajaUsuario().getUsuario().getId(), movimiento.getCajaUsuario().getCaja().getId());
		
		if(cajaUsuario == null || !cajaUsuario.isActiva()){
			return null;
		}
				
//		TipoPago tipoPago = this.lstAllTipoPagos().stream()
//				.filter( t -> t.getId() == movimiento.getTipoPago().getId())
//				.collect(Collectors.toList())
//				.get(0);
//		
//		TipoMovimiento tipoMovimiento = this.lstAllTipoMovimientos().stream()
//				.filter( t -> t.getId() == movimiento.getTipoMovimiento().getId())
//				.collect(Collectors.toList())
//				.get(0);

		Pedido pedido = pedidoService.findPedidoById(movimiento.getPedido().getId());
		log.info("MovimientoServiceImpl.saveMovimiento... estadoPedido={}", pedido.getEstadoPedido().getEstado());
				
		if(pedido == null ){return null;}
		
		BigDecimal newSaldo = BigDecimal.valueOf(0);
		if(movimiento.getPedido().getTipoPedido().getNombre().equalsIgnoreCase("VENTA AL CLIENTE"))
		{
			newSaldo = pedido.getSaldoPedido().subtract(movimiento.getIngresoDinero());
			if (newSaldo.intValue() >= 0) {
				movimiento.setEgresoDinero(BigDecimal.valueOf(0));
			}
			if (newSaldo.intValue() < 0) {
				movimiento.setEgresoDinero(newSaldo.abs());//- con abs cambiamos a +
			}
		}
		if(movimiento.getPedido().getTipoPedido().getNombre().equalsIgnoreCase("COMPRA O ADQUISICION"))
		{
			newSaldo = pedido.getSaldoPedido().subtract(movimiento.getEgresoDinero());
			if (newSaldo.intValue() >= 0) {
				movimiento.setIngresoDinero(BigDecimal.valueOf(0));
			}
			if (newSaldo.intValue() < 0) {
				movimiento.setIngresoDinero(newSaldo.abs());
			}
		}
		movimiento.setCajaUsuario(cajaUsuario);

		//movimiento.setTipoPago(tipoPago);
		//movimiento.setTipoMovimiento(tipoMovimiento);
//		movimiento.setTipoPago(new TipoPago());
//		movimiento.getTipoPago().setId(movimiento.getTipoPago().getId());
//		
//		movimiento.setTipoMovimiento(new TipoMovimiento());
//		movimiento.getTipoMovimiento().setId(movimiento.getTipoMovimiento().getId());
		
		movimiento.setPedido(pedido);
		newMovimiento = movimientoDao.save(movimiento);
		newMovimiento.setPedido(pedidoService.updatePedido(newMovimiento.getPedido(), movimiento.getPedido().getId()));
		newMovimiento.setCajaUsuario(cajaUsuarioService.updateCajaUsuario(newMovimiento.getCajaUsuario(), movimiento.getCajaUsuario().getId() ));
		return newMovimiento;
	};

}
