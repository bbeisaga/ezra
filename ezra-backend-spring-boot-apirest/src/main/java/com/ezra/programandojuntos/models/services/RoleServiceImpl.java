package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezra.programandojuntos.models.dao.IRoleDao;
import com.ezra.programandojuntos.models.entity.Modulo;
import com.ezra.programandojuntos.models.entity.Role;

@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	IRoleDao roleDao;

	@Override
	public List<Role> allRoles() {
		return roleDao.findAll();
	}

	@Override
	public List<Modulo> allModulos() {
		return roleDao.findAllModulos();
	}

}
