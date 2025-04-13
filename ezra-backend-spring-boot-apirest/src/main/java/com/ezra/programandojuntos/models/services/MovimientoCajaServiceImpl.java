package com.ezra.programandojuntos.models.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.IMovimientoCajaDao;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.entity.TipoMovimientoCaja;

@Service
public class MovimientoCajaServiceImpl implements IMovimientoCajaService {
	
	Logger log = LoggerFactory.getLogger(MovimientoCajaServiceImpl.class);
	
	@Autowired
	IMovimientoCajaDao movimientoCajaDao;
	
	
	@Autowired
	ICajaUsuarioService cajaUsuarioService;
	

	@Override
	@Transactional(readOnly = true)
	public List<TipoMovimientoCaja> lstAllTipoMovimientosCaja(){
		return movimientoCajaDao.findAllTipoMovimientosCaja();
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
		if(movimiento.getTipoMovimientoCaja().getTipo().equalsIgnoreCase("I")) {
			newSaldoCaja = cajaUsuario.getSaldoCaja().add(movimiento.getIngresoDinero());
			if (newSaldoCaja.intValue() > 0) {
				movimiento.setEgresoDinero(BigDecimal.valueOf(0));
			}		
		}
		
		if(movimiento.getTipoMovimientoCaja().getTipo().equalsIgnoreCase("E")) {
			newSaldoCaja = cajaUsuario.getSaldoCaja().subtract(movimiento.getEgresoDinero());
			if (newSaldoCaja.intValue() > 0) {
				movimiento.setIngresoDinero(BigDecimal.valueOf(0));
				movimiento.setEgresoDinero(movimiento.getEgresoDinero());		
			} else {
				return null;
				//el saldo en caja no es suficiente para la transaccion
			}
		}
//		cajaUsuario.setSaldoCaja(newSaldoCaja);
//		movimiento.setCajaUsuario(cajaUsuario);
//		movimiento.setTipoMovimiento(new TipoMovimiento());
//		movimiento.getTipoMovimiento().setId(movimiento.getTipoMovimiento().getId());
		newMovimiento = movimientoCajaDao.save(movimiento);
		newMovimiento.setCajaUsuario(cajaUsuarioService.updateCajaUsuario(newMovimiento.getCajaUsuario(), movimiento.getCajaUsuario().getId() ));
		return newMovimiento;
	};
	


}
