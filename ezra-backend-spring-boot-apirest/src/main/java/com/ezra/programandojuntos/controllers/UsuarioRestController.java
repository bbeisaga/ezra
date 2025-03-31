package com.ezra.programandojuntos.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import org.springframework.web.multipart.MultipartFile;

import com.ezra.programandojuntos.enums.SortActiveCliente;
import com.ezra.programandojuntos.enums.SortDirection;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.TipoDocumento;
import com.ezra.programandojuntos.models.entity.Usuario;
import com.ezra.programandojuntos.models.services.IClienteService;
import com.ezra.programandojuntos.models.services.IUploadFileService;
import com.ezra.programandojuntos.models.services.IUsuarioService;

import jakarta.validation.Valid;

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
	
}
