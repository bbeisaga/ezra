package com.ezra.programandojuntos.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.enums.SortActiveUsuario;
import com.ezra.programandojuntos.enums.SortDirection;
import com.ezra.programandojuntos.models.entity.Modulo;
import com.ezra.programandojuntos.models.entity.Role;
import com.ezra.programandojuntos.models.entity.Usuario;
import com.ezra.programandojuntos.models.services.IRoleService;
import com.ezra.programandojuntos.models.services.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" }, originPatterns = {"*"})
@RestController
@RequestMapping("/api")
public class RoleRestController {
	
	Logger log = LoggerFactory.getLogger(RoleRestController.class);

	@Autowired
	private IRoleService roleService;
	
	
	@GetMapping("/roles")
	public List<Role> allRoles() {
		return roleService.allRoles();
	}
	
	@GetMapping("/roles/modulos")
	public List<Modulo> allModulos() {
		return roleService.allModulos();
	}
	

}
