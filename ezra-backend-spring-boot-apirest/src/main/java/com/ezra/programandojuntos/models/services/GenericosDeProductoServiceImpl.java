package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezra.programandojuntos.models.dao.GenericosDeProductoDao;
import com.ezra.programandojuntos.models.entity.GenericosDeProducto;

@Service
public class GenericosDeProductoServiceImpl implements IGenericosDeProductoService {
	
	@Autowired
	private GenericosDeProductoDao genericosDeProductodao;

	@Override
	public List<GenericosDeProducto> findAlll() {
		return (List<GenericosDeProducto>) genericosDeProductodao.findAll();
	}

}
