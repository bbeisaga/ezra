package com.ezra.programandojuntos.models.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.dto.CajaUsuarioReporte;
import com.ezra.programandojuntos.dto.PedidoReporte;
import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.models.dao.ICajaUsuarioDao;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.repository.CajaUsuarioRepository;
import com.ezra.programandojuntos.util.ExcelUtil;
import com.ezra.programandojuntos.models.entity.Movimiento;

@Service
public class CajaUsuarioServiceImpl implements ICajaUsuarioService {

	@Autowired
	private ICajaUsuarioDao cajaUsuarioDao;
	
	@Autowired CajaUsuarioRepository cajaUsuarioRepository;
	
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
			Map<String, BigDecimal> movPorCju = movimientoPorCajaUsuario(cajaUsuario.getId());
			Map<String, BigDecimal> movCajaPorCju = movimientoCajaPorCajaUsuario(cajaUsuario.getId());

			cjActual.setIngresoEsperado(
					movPorCju.get("ingreso").add(
					movCajaPorCju.get("ingreso")));
			cjActual.setEgresoEsperado(
					movPorCju.get("egreso").add(
					movCajaPorCju.get("egreso")));
			cjActual.setSaldoCaja(
					movPorCju.get("flujoEfectivo").add(			
					movCajaPorCju.get("flujoEfectivo")));
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
	public Map<String, BigDecimal> movimientoPorCajaUsuario(Long cajaUsuarioId){
		CajaUsuario cju = findById(cajaUsuarioId);
		List<Movimiento> items= cju.getMovimientos();
		//BigDecimal saldoCju = new BigDecimal(0);
		BigDecimal ingresoCju = new BigDecimal(0);
		BigDecimal egresoCju = new BigDecimal(0);

		for (Movimiento item : items) {
			ingresoCju = ingresoCju.add(item.getIngresoDinero()); //+
			egresoCju = egresoCju.add(item.getEgresoDinero()); // -
		}
		//saldoCju = ingresoCju.subtract(egresoCju);				
        Map<String, BigDecimal> mapa = new HashMap<String, BigDecimal>();
        mapa.put("ingreso", ingresoCju);
        mapa.put("egreso", egresoCju);
        mapa.put("flujoEfectivo", ingresoCju.subtract(egresoCju));
		return mapa;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, BigDecimal> movimientoCajaPorCajaUsuario(Long cajaUsuarioId){
		CajaUsuario cju = findById(cajaUsuarioId);
		List<MovimientoCaja> items= cju.getMovimientosCaja();
		//BigDecimal saldoCju = new BigDecimal(0);
		BigDecimal ingresoCajaCju = new BigDecimal(0);
		BigDecimal egresoCajaCju = new BigDecimal(0);

		for (MovimientoCaja item : items) {
			ingresoCajaCju = ingresoCajaCju.add(item.getIngresoDinero()); //+
			egresoCajaCju = egresoCajaCju.add(item.getEgresoDinero()); // -
		}
		//saldoCju = ingresoCju.add(egresoCju);
        Map<String, BigDecimal> mapa = new HashMap<String, BigDecimal>();
        mapa.put("ingreso", ingresoCajaCju);
        mapa.put("egreso", egresoCajaCju);
        mapa.put("flujoEfectivo", ingresoCajaCju.subtract(egresoCajaCju));
		return mapa;
	}
	
	public void persistCajaUsuario ( CajaUsuario cajaUsuario) {
		cajaUsuario.setFechaApertura(new Date());
		cajaUsuario.setActiva(true);
		cajaUsuario.setSaldoPorConteo(new BigDecimal(0));
		cajaUsuarioDao.persistCajaUsuario(cajaUsuario.getFechaApertura(), 
										cajaUsuario.getSaldoCaja(), 
										cajaUsuario.getSaldoPorConteo(), 
										cajaUsuario.isActiva(), 
										cajaUsuario.getCaja().getId(), 
										cajaUsuario.getUsuario().getId());
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
	
	
	@Override
	public ByteArrayInputStream createReportCajaUsuario(ReportArray reporte) {
		String[] cabeceraReporte = null;
	
			cabeceraReporte = new String[] {
				"Caja", "Apellido Usuario", "Nombre Usuario", "Fecha apertura", "Fecha actualización", "Fecha cierre", "Ingresos S/.", 
				"Egresos S/.", "Saldo S/.", "Saldo x conteo S/."};
		
		XSSFWorkbook excelbook = new XSSFWorkbook();
		XSSFSheet excelHoja = excelbook.createSheet("data");
		ExcelUtil.generarCabecera(excelbook, excelHoja, cabeceraReporte);
		
		XSSFCellStyle style =ExcelUtil.crearStyle(excelbook, 
							HorizontalAlignment.CENTER, 
							HSSFColorPredefined.GREY_25_PERCENT.getIndex(), 
							(short) 10, 
							HSSFColorPredefined.BLACK.getIndex());
		
		int numFila = 1;
		List<CajaUsuarioReporte> cjs = cajaUsuarioRepository.listarCajaUsuarioConFiltros(reporte);
				
		for(CajaUsuarioReporte p: cjs) {
			short numColumn = 0;
			XSSFRow fila = excelHoja.createRow(numFila);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getNombreCaja(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getApellidoUsuario(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getNombreUsuario(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getFechaApertura(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getFechaActualizacion(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getFechaCierre(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getIngresoEsperado().doubleValue(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getEgresoEsperado().doubleValue(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getSaldoCaja().doubleValue(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getSaldoPorConteo().doubleValue(), style);
			numFila++;
		};
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			excelbook.write(out);
		} catch (IOException e) {
			e.getCause();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

}
