package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.dto.CajaDto;
import com.ezra.programandojuntos.models.dao.ICajaDao;
import com.ezra.programandojuntos.models.entity.Caja;
import com.ezra.programandojuntos.models.repository.CajaRepository;

@Service
public class CajaServiceImpl implements ICajaService {

	@Autowired
	private ICajaDao cajaDao;
	
	
	@Autowired
	private CajaRepository cajaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Caja> findAll() {
		return (List<Caja>) cajaDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CajaDto> listCajasPorAsignar() {
		return cajaRepository.listarCajasPorAsignar();
	}
	




}
