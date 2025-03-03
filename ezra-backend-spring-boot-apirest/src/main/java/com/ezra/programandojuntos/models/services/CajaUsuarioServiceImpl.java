package com.ezra.programandojuntos.models.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.ICajaUsuarioDao;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.entity.Movimiento;

@Service
public class CajaUsuarioServiceImpl implements ICajaUsuarioService {

	@Autowired
	private ICajaUsuarioDao cajaUsuarioDao;
	
	@Override
	@Transactional
	public CajaUsuario registerCajaUsuario(CajaUsuario cajaUsuario) {
		return cajaUsuarioDao.save(cajaUsuario);
	}
	
	public CajaUsuario updateCajaUsuario(CajaUsuario cajaUsuario, Long id) {
		CajaUsuario cjActual = findById(id);
		
//		if (cjActual == null) {
//		response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
//				.concat(id.toString().concat(" no existe en la base de datos!")));
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
//		}
		
		if (cjActual!=null) {
			Map<String, BigDecimal> movimientos = movimientoDeCajaUsuario(cajaUsuario.getId());
			Map<String, BigDecimal> movimientosCju = movimientoCajaDeCajaUsuario(cajaUsuario.getId());

			cjActual.setIngresoEsperado(
					movimientos.get("ingresos").add(
					movimientosCju.get("ingresoCju")));
			cjActual.setEgresoEsperado(
					movimientos.get("egresos").add(
					movimientosCju.get("egresoCju")));
			cjActual.setSaldoCaja(
					movimientos.get("saldos").add(			
					movimientosCju.get("saldoCju")));
			cjActual.setSaldoPorConteo(cajaUsuario.getSaldoPorConteo());
			cjActual.setActiva(cajaUsuario.isActiva());
			if(!cajaUsuario.isActiva()) {
				cjActual.setFechaCierre(new Date());
			}
		}
		return cajaUsuarioDao.save(cjActual);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, BigDecimal> movimientoDeCajaUsuario(Long cajaUsuarioId){
		CajaUsuario cju = findById(cajaUsuarioId);
		List<Movimiento> items= cju.getMovimientos();
		BigDecimal saldoCju = new BigDecimal(0);
		BigDecimal ingresoCju = new BigDecimal(0);
		BigDecimal egresoCju = new BigDecimal(0);

		for (Movimiento item : items) {
			ingresoCju = ingresoCju.add(item.getIngresoDinero()); //+
			egresoCju = egresoCju.add(item.getEgresoDinero()); // -
		}
		saldoCju = ingresoCju.subtract(egresoCju);				
        Map<String, BigDecimal> mapa = new HashMap<String, BigDecimal>();
        mapa.put("ingresos", ingresoCju);
        mapa.put("egresos", egresoCju);
        mapa.put("saldos", saldoCju);
		return mapa;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, BigDecimal> movimientoCajaDeCajaUsuario(Long cajaUsuarioId){
		CajaUsuario cju = findById(cajaUsuarioId);
		List<MovimientoCaja> items= cju.getMovimientosCaja();
		BigDecimal saldoCju = new BigDecimal(0);
		BigDecimal ingresoCju = new BigDecimal(0);
		BigDecimal egresoCju = new BigDecimal(0);

		for (MovimientoCaja item : items) {
			ingresoCju = ingresoCju.add(item.getIngresoDinero()); //+
			egresoCju = egresoCju.add(item.getEgresoDinero()); // -
		}
		saldoCju = ingresoCju.add(egresoCju);
        Map<String, BigDecimal> mapa = new HashMap<String, BigDecimal>();
        mapa.put("ingresoCju", ingresoCju);
        mapa.put("egresoCju", egresoCju);
        mapa.put("saldoCju", saldoCju);
		return mapa;
	}
	
	public void persistCajaUsuario ( CajaUsuario cajaUsuario) {
		cajaUsuario.setFechaApertura(new Date());
		cajaUsuario.setActiva(true);
		cajaUsuarioDao.persistCajaUsuario(cajaUsuario.getFechaApertura(), cajaUsuario.getSaldoCaja(), cajaUsuario.isActiva(), cajaUsuario.getCaja().getId(), cajaUsuario.getUsuario().getId());
	}
	
	@Override
	@Transactional(readOnly = true)
	public CajaUsuario findUltimaCajaUsuarioByUserName(String userName) {
		return cajaUsuarioDao.findCajaUsuarioByUserName(userName);
	}

	@Override
	@Transactional(readOnly = true)
	public CajaUsuario findCajaUsuarioByUserIdAndCajaId(Long userId, Byte cajaId) {
		return cajaUsuarioDao.findCajaUsuarioByUserIdAndCajaId(userId, cajaId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CajaUsuario findCajaUsuarioByUsernameAndCajaId(String username, Byte cajaId) {
		return cajaUsuarioDao.findCajaUsuarioByUsernameAndCajaId(username, cajaId);
	}

	
	@Override
	@Transactional(readOnly = true)
	public CajaUsuario findById(Long id) {
		return cajaUsuarioDao.findById(id).orElse(null);
	}

}
