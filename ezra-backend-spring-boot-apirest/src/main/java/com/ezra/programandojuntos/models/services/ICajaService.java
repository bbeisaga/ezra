package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.entity.Caja;
import com.ezra.programandojuntos.models.entity.CajaUsuario;

public interface ICajaService {

	public List<Caja> findAll();
	
	public CajaUsuario guardar(CajaUsuario cajaUsuario);
	
	public void persistCajaUsuario ( CajaUsuario cajausuario); 
	

	public CajaUsuario findUltimaCajaUsuarioByUserName(String userName);
	
	public CajaUsuario findCajaUsuarioByUserIdAndCajaId(Long userId, Byte cajaId);

	
	
	public CajaUsuario findById(Long id);

	
	

}
