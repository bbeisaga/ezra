package com.ezra.programandojuntos.controllers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.enums.SortActiveUsuario;
import com.ezra.programandojuntos.enums.SortDirection;
import com.ezra.programandojuntos.exceptions.ClienteExceptions;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.Usuario;
import com.ezra.programandojuntos.models.services.IUsuarioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" }, originPatterns = {"*"})
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
	
	@PutMapping("/usuarios-roles/update/{usuarioId}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long usuarioId) {

		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			Usuario usuarioUpdated  = usuarioService.updateRolUsuario(usuario, usuarioId);
			response.put("mensaje", "El usuario ha sido actualizado con éxito!");
			response.put("usuario", usuarioUpdated);
		} catch (SQLException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/usuarios-roles/delete/{usuarioId}")
	public ResponseEntity<?> delete(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long usuarioId) {

		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			usuarioService.deleteRolUsuarioForGroup(usuario, usuarioId);
			response.put("mensaje", "Se ha eliminado roles al usuario con exito");
		} catch (SQLException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
