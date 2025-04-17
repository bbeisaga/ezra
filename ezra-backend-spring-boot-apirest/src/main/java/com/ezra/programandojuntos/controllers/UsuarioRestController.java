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
import com.ezra.programandojuntos.models.entity.Usuario;
import com.ezra.programandojuntos.models.services.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	
	Logger log = LoggerFactory.getLogger(UsuarioRestController.class);


	@Autowired
	private IUsuarioService usuarioService;
	
//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/usuarios/{username}")
	public Usuario usuarioByUsername(@PathVariable String username) {
		Usuario newUsuario = usuarioService.findByUsername(username);
		newUsuario.setPassword("");
		newUsuario.setRoles(null);
		return newUsuario;
	}
	
	@GetMapping("/usuarios")
	public List<Usuario> allUsuarios() {
		//List<Usuario> usuarios = usuarioService.getAllUsuarios();
		return usuarioService.getAllUsuarios();
	}
	
	@GetMapping("/usuarios/pageable")
	public Page<Usuario> index(@RequestParam int pageNumber, @RequestParam int pageSize, 
								@RequestParam SortActiveUsuario active,
								@RequestParam SortDirection direction,
								@RequestParam String query) {
		
        log.debug("UsuarioRestController.index(pageSize: {}, pageNumber: {}, query{}) ", pageSize, pageNumber, query );
       
//      Pageable pageRequest = PageRequest.of(pageNumber, pageSize,
//					Sort.by(direction.toString() ,active.getValue()));
        Pageable pageRequest = null;
        if(direction.getValue()=="desc") {
        	pageRequest = PageRequest.of(pageNumber, pageSize,
					Sort.by(active.getValue()).descending());
        } else {
         	pageRequest = PageRequest.of(pageNumber, pageSize,
					Sort.by(active.getValue()).ascending());
        }
		return usuarioService.findAllUsuarioPageable(query, pageRequest );
	}
	

	
}
