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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ezra.programandojuntos.enums.SortActiveCliente;
import com.ezra.programandojuntos.enums.SortDirection;
import com.ezra.programandojuntos.exceptions.ClienteExceptions;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.TipoDocumento;
import com.ezra.programandojuntos.models.services.IClienteService;
import com.ezra.programandojuntos.models.services.IUploadFileService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadService;

	// private final Logger log =
	// LoggerFactory.getLogger(ClienteRestController.class);
	// @Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
//	@GetMapping("/clientes/page/{page}")
//	public Page<Cliente> index(@PathVariable Integer page) {
//		Pageable pageable = PageRequest.of(page, 4);
//		return clienteService.findAll(pageable);
//	}

//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/clientes/pageable")
	public Page<Cliente> index(@RequestParam int pageNumber, @RequestParam int pageSize,
			@RequestParam SortActiveCliente active, @RequestParam SortDirection direction, @RequestParam String query) {

		log.debug("ClienteRestController.index(pageSize: {}, pageNumber: {}, query{}) ", pageSize, pageNumber, query);

//      Pageable pageRequest = PageRequest.of(pageNumber, pageSize,
//					Sort.by(direction.toString() ,active.getValue()));
		Pageable pageRequest = null;
		if (direction.getValue() == "desc") {
			pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(active.getValue()).descending());
		} else {
			pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(active.getValue()).ascending());
		}
		return clienteService.findAllClientePageable(query, pageRequest);
	}

	@GetMapping("/clientes/filtrar-cliente/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> filtrarClientes(@PathVariable String term) {
		return clienteService.findClienteByNomApellRz(term);
	}

	@GetMapping("/clientes/numero-documento/{numero}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> filtrarClienteNumDoc(@PathVariable String numero) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cliente = clienteService.findByNumeroDocumento(numero);
			response.put("cliente", cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("mensaje",
					"El cliente ID: ".concat(numero.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/clientes/celular/{celular}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> filtrarClienteNumCelular(@PathVariable String celular) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cliente = clienteService.findByNumeroCelular(celular);
			response.put("cliente", cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("mensaje",
					"El cliente ID: ".concat(celular.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/clientes/usuario/{usuarioId}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente getClienteUsuarioId(@PathVariable Long usuarioId) {
		return clienteService.findClienteByUsuarioId(usuarioId);
	}

//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cliente = clienteService.findById(id);
			response.put("mensaje", "El cliente ha sido creado con éxito!");
			response.put("cliente", cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

//	@Secured("ROLE_ADMIN")
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {

		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			clienteNew = clienteService.guardarCliente(cliente);
			response.put("mensaje", "El cliente ha sido creado con éxito!");
			response.put("cliente", clienteNew);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("err", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ClienteExceptions ce) {
			response.put("mensaje", ce.getMessage());
			response.put("err", "Error cliente");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

//	@Secured("ROLE_ADMIN")
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			Cliente clienteUpdated = clienteService.actualizar(cliente, id);
			response.put("mensaje", "El cliente ha sido actualizado con éxito!");
			response.put("cliente", clienteUpdated);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

//	@Secured("ROLE_ADMIN")
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			// Cliente cliente = clienteService.findById(id);
			// String nombreFotoAnterior = cliente.getFoto();

			// uploadService.eliminar(nombreFotoAnterior);

			clienteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
//	@PostMapping("/clientes/upload")
//	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
//		Map<String, Object> response = new HashMap<>();
//		
//		Cliente cliente = clienteService.findById(id);
//		
//		if(!archivo.isEmpty()) {
//
//			String nombreArchivo = null;
//			try {
//				nombreArchivo = uploadService.copyFileToPath(archivo);
//			} catch (IOException e) {
//				response.put("mensaje", "Error al subir la imagen del cliente");
//				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
//				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//			
//			clienteService.insertar(cliente);
//			
//			response.put("cliente", cliente);
//			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
//			
//		}
//		
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
//	}

	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		Resource recurso = null;

		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

//	@Secured("ROLE_ADMIN")
//	@GetMapping("/clientes/regiones")
//	public List<Region> listarRegiones(){
//		return clienteService.findAllRegiones();
//	}

//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/clientes/documentos")
	public List<TipoDocumento> listarDocumentos() {
		return clienteService.findAllTipoDocumento();
	}

//	@Secured("ROLE_ADMIN")
//	@PutMapping("/clientes/{id}")
//	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
//	
//		Cliente clienteActual = clienteService.findById(id);
//	
//		Cliente clienteUpdated = null;
//	
//		Map<String, Object> response = new HashMap<>();
//	
//		if(result.hasErrors()) {
//	
//			List<String> errors = result.getFieldErrors()
//					.stream()
//					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
//					.collect(Collectors.toList());
//			
//			response.put("errors", errors);
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
//		}
//		
//		if (clienteActual == null) {
//			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
//					.concat(id.toString().concat(" no existe en la base de datos!")));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
//		}
//	
//		try {
//	
//			clienteActual.setApellido(cliente.getApellido());
//			clienteActual.setNombre(cliente.getNombre());
//			clienteActual.setEmail(cliente.getEmail());
//			clienteActual.setCreateAt(cliente.getCreateAt());
//			//clienteActual.setRegion(cliente.getRegion());
//			clienteActual.setTipoDocumento(cliente.getTipoDocumento());
//	
//			clienteUpdated = clienteService.save(clienteActual);
//	
//		} catch (DataAccessException e) {
//			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
//			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	
//		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
//		response.put("cliente", clienteUpdated);
//	
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
//	}
}
