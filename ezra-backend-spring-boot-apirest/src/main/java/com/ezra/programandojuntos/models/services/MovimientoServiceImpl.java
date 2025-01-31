package com.ezra.programandojuntos.models.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.ICajaUsuarioDao;
import com.ezra.programandojuntos.models.dao.IMovimientoDao;
import com.ezra.programandojuntos.models.dao.IPedidoDao;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;
import com.ezra.programandojuntos.models.entity.TipoPago;

@Service
public class MovimientoServiceImpl implements IMovimientoService {
	
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
				
		TipoPago tipoPago = this.lstAllTipoPagos().stream()
				.filter( t -> t.getId() == movimiento.getTipoPago().getId())
				.collect(Collectors.toList())
				.get(0);
		
		TipoMovimiento tipoMovimiento = this.lstAllTipoMovimientos().stream()
				.filter( t -> t.getId() == movimiento.getTipoMovimiento().getId())
				.collect(Collectors.toList())
				.get(0);
		
		if( tipoPago == null || tipoMovimiento == null ){return null;}

		Pedido pedido = pedidoService.findPedidoById(movimiento.getPedido().getId());
			
		if(pedido == null ){return null;}
		BigDecimal newSaldo = pedido.getSaldoPedido().subtract(movimiento.getIngresoDinero());
		if (newSaldo.intValue() >= 0) {
			movimiento.setEgresoDinero(BigDecimal.valueOf(0));
		}
		if (newSaldo.intValue() < 0) {
			movimiento.setEgresoDinero(newSaldo);
		}
		movimiento.setCajaUsuario(cajaUsuario);
		movimiento.setTipoPago(tipoPago);
		movimiento.setTipoMovimiento(tipoMovimiento);
		movimiento.setPedido(pedido);
		newMovimiento = movimientoDao.save(movimiento);
		newMovimiento.setPedido(pedidoService.updatePedido(newMovimiento.getPedido(), movimiento.getPedido().getId()));
		newMovimiento.setCajaUsuario(cajaUsuarioService.updateCajaUsuario(newMovimiento.getCajaUsuario(), movimiento.getCajaUsuario().getId() ));
		return newMovimiento;
	};

}
