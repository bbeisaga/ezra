package com.ezra.programandojuntos.models.services;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.IMovimientoCajaDao;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;

@Service
public class MovimientoCajaServiceImpl implements IMovimientoCajaService {
	
	Logger log = LoggerFactory.getLogger(MovimientoCajaServiceImpl.class);
	
	@Autowired
	IMovimientoCajaDao movimientoDao;
	
	
	@Autowired
	ICajaUsuarioService cajaUsuarioService;
	

	@Override
	@Transactional(readOnly = true)
	public List<TipoMovimiento> lstAllTipoMovimientos(){
		return movimientoDao.findAllTipoMovimientos();
	}
	
	@Override
	@Transactional
	public MovimientoCaja saveMovimiento (MovimientoCaja movimiento){
		log.info("MovimientoServiceImpl.saveMovimiento... movimiento={}", movimiento);

		MovimientoCaja newMovimiento = null;
		CajaUsuario cajaUsuario = cajaUsuarioService
				.findCajaUsuarioByUserIdAndCajaId(movimiento.getCajaUsuario().getUsuario().getId(), movimiento.getCajaUsuario().getCaja().getId());
		
		if(cajaUsuario == null || !cajaUsuario.isActiva()){
			return null;
		}
		
		BigDecimal newSaldoCaja = BigDecimal.valueOf(0);
		if(movimiento.getTipoMovimiento().getTipo().equalsIgnoreCase("I")) {
			newSaldoCaja = cajaUsuario.getSaldoCaja().add(movimiento.getIngresoDinero());
			if (newSaldoCaja.intValue() > 0) {
				movimiento.setEgresoDinero(BigDecimal.valueOf(0));
			}		
		}
		
		if(movimiento.getTipoMovimiento().getTipo().equalsIgnoreCase("E")) {
			newSaldoCaja = cajaUsuario.getSaldoCaja().subtract(movimiento.getEgresoDinero());
			if (newSaldoCaja.intValue() > 0) {
				movimiento.setIngresoDinero(BigDecimal.valueOf(0));
				movimiento.setEgresoDinero(movimiento.getEgresoDinero().multiply(BigDecimal.valueOf(-1)));		
			} else {
				return null;
				//el saldo en caja no es suficiente para la transaccion
			}
		}
//		cajaUsuario.setSaldoCaja(newSaldoCaja);
//		movimiento.setCajaUsuario(cajaUsuario);
//		movimiento.setTipoMovimiento(new TipoMovimiento());
//		movimiento.getTipoMovimiento().setId(movimiento.getTipoMovimiento().getId());
		newMovimiento = movimientoDao.save(movimiento);
		newMovimiento.setCajaUsuario(cajaUsuarioService.updateCajaUsuario(newMovimiento.getCajaUsuario(), movimiento.getCajaUsuario().getId() ));
		return newMovimiento;
	};

}
