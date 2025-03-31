package com.ezra.programandojuntos.models.services;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Map;

import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.models.entity.CajaUsuario;

public interface ICajaUsuarioService {
	
	public CajaUsuario registerCajaUsuario(CajaUsuario cajaUsuario);
	
	public CajaUsuario updateCajaUsuario(CajaUsuario cajaUsuario, Long id);


	public void persistCajaUsuario ( CajaUsuario cajausuario); 
	

	public CajaUsuario findUltimaCajaUsuarioByUserName(String userName);
	
	public CajaUsuario findCajaUsuarioByUserIdAndCajaId(Long userId, Byte cajaId);

	public CajaUsuario findCajaUsuarioByUsernameAndCajaId(String username, Byte cajaId);

	public CajaUsuario findById(Long id);
	
	public Map<String, BigDecimal> movimientoDeCajaUsuario(Long cajaUsuarioId);
	
	public Map<String, BigDecimal> movimientoCajaDeCajaUsuario(Long cajaUsuarioId);


	public ByteArrayInputStream createReportCajaUsuario(ReportArray reporte);
	//public CajaUsuario saveCajaUusuario (CajaUsuario cajaUsuario);

}
