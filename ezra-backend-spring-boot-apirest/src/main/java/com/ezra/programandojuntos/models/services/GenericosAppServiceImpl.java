package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezra.programandojuntos.models.dao.GenericosAppDao;
import com.ezra.programandojuntos.models.entity.GenericosApp;

@Service
public class GenericosAppServiceImpl implements IGenericosAppService {
	
	@Autowired
	private GenericosAppDao genericosDeProductodao;

	@Override
	public List<GenericosApp> findAlll() {
		return (List<GenericosApp>) genericosDeProductodao.findAll();
	}

}
