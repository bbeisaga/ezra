package com.ezra.programandojuntos.models.services;

import java.util.List;

import com.ezra.programandojuntos.models.entity.Modulo;
import com.ezra.programandojuntos.models.entity.Role;

public interface IRoleService {
	
	public List<Role> allRoles();
	
	public List<Modulo> allModulos();

}
