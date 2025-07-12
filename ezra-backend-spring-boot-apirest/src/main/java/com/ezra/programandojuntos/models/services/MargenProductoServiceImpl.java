package com.ezra.programandojuntos.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezra.programandojuntos.models.dao.IMargenProductoDao;

@Service
public class MargenProductoServiceImpl implements IMargenProductoService {
	
	@Autowired
	IMargenProductoDao margenProductoDao;

	@Override
	public void deleteMargenProductoById(Long margenProductoId) {
		margenProductoDao.deleteById(margenProductoId);
	}

}
