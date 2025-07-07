package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.dto.CajaDto;
import com.ezra.programandojuntos.models.entity.Caja;
import com.ezra.programandojuntos.models.entity.CajaUsuario;

public interface ICajaService {

	public List<Caja> findAll();
	
	public List<CajaDto> listCajasPorAsignar();
	
}
