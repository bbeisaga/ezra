package com.ezra.programandojuntos.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.enums.SortActiveProducto;
import com.ezra.programandojuntos.enums.SortDirection;
import com.ezra.programandojuntos.models.entity.Categoria;
import com.ezra.programandojuntos.models.entity.Color;
import com.ezra.programandojuntos.models.entity.Material;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.Uso;
import com.ezra.programandojuntos.models.services.ProductoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ProductoRestController {
	
	Logger log = LoggerFactory.getLogger(ProductoRestController.class);


	@Autowired
	private ProductoService productoService;
	

//	@Autowired
//	private IClienteService clienteService;
//	
//	@Autowired
//	private IUploadFileService uploadService;
//	

	
	@GetMapping("/productos/pageable")
	public Page<Producto> index(@RequestParam int pageNumber, @RequestParam int pageSize, 
								@RequestParam SortActiveProducto active,
								@RequestParam SortDirection direction,
								@RequestParam String query) {
		
        log.debug("ProductoRestController.index(pageSize: {}, pageNumber: {}, query{}) ", pageSize, pageNumber, query );
        Pageable pageRequest = null;
        if(direction.getValue()=="desc") {
        	pageRequest = PageRequest.of(pageNumber, pageSize,
					Sort.by(active.getValue()).descending());
        } else {
         	pageRequest = PageRequest.of(pageNumber, pageSize,
					Sort.by(active.getValue()).ascending());
        }
		return productoService.findAllProductoPageable(query, pageRequest );
	}
	
	//@Secured({"ROLE_ADMIN"})
	@GetMapping("/producto/filtrar-productos/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<Producto> filtrarProductos(@PathVariable String term){
		return productoService.findProductoByNombre(term);
	}
	
	@GetMapping("/producto/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Producto producto = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			producto = productoService.findProductoById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(producto == null) {
			response.put("mensaje", "El producto ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	@PostMapping("/producto")
	public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result) {
		Producto productoNew = null;
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
			productoNew = productoService.crear(producto);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto ha sido creado con éxito!");
		response.put("producto", productoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/producto/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Producto producto, BindingResult result, @PathVariable Long id) {
		Producto productoUpdated = null;
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
			productoUpdated = productoService.actualizar(producto, id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto ha sido actualizado con éxito!");
		response.put("producto", productoUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
//	
//	@DeleteMapping("/clientes/{id}")
//	public ResponseEntity<?> delete(@PathVariable Long id) {
//		
//		Map<String, Object> response = new HashMap<>();
//		
//		try {	
//		    clienteService.delete(id);
//		} catch (DataAccessException e) {
//			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
//			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		response.put("mensaje", "El cliente eliminado con éxito!");
//		
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
//	}
//	
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
//				nombreArchivo = uploadService.copiar(archivo);
//			} catch (IOException e) {
//				response.put("mensaje", "Error al subir la imagen del cliente");
//				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
//				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//						
//			clienteService.save(cliente);
//			
//			response.put("cliente", cliente);
//			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
//			
//		}
//		
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
//	}
//	
//	@GetMapping("/uploads/img/{nombreFoto:.+}")
//	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
//
//		Resource recurso = null;
//		
//		try {
//			recurso = uploadService.cargar(nombreFoto);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		
//		HttpHeaders cabecera = new HttpHeaders();
//		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
//		
//		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
//	}
//	
//
	@GetMapping("/producto/colores")
	public List<Color> listarColores(){
		return productoService.findAllColores();
	}

	@GetMapping("/producto/materiales")
	public List<Material> listarMateriales(){
		return productoService.findAllMateriales();
	}
	
	@GetMapping("/producto/categorias")
	public List<Categoria> listarCategorias(){
		return productoService.findAllCategoriass();
	}
	
	@GetMapping("/producto/usos")
	public List<Uso> listarUsos(){
		return productoService.findAllUsos();
	}

}
