package com.ezra.programandojuntos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.enums.SortActivePedido;
import com.ezra.programandojuntos.enums.SortDirection;
import com.ezra.programandojuntos.exceptions.PedidoExceptions;
import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.TipoPedido;
import com.ezra.programandojuntos.models.services.IPedidoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PedidoRestController {
	
	Logger log = LoggerFactory.getLogger(PedidoRestController.class);
	
	@Autowired
	private IPedidoService pedidoService;
	
//	@Autowired
//	private ProductoService productoService;
	
	//@Secured({"ROLE_ADMIN" , "ROLE_USER"})
	@GetMapping("/pedidos")
	@ResponseStatus(HttpStatus.OK)
	public List<Pedido> listarPedidoAll() {
		return pedidoService.findPedidoAll();
	}

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/pedidos/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Pedido show(@PathVariable Long id) {
		return pedidoService.findPedidoById(id);
	}
	
	//@Secured({"ROLE_ADMIN"})
//	@GetMapping("/pedidos/filtrar-productos/{term}")
//	@ResponseStatus(HttpStatus.OK)
//	public List<Producto> filtrarProductos(@PathVariable String term){
//		return productoService.findProductoByNombre(term);
//	}
	
	//@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/pedidos/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		pedidoService.deletePedidoById(id);
	}
	

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/pedidos/pageable")
	public Page<Pedido> listarPedidoAllPageable(
								@RequestParam int pageNumber, 
								@RequestParam int pageSize, 
								@RequestParam SortActivePedido active,
								@RequestParam SortDirection direction,
								@RequestParam String query) {
		
        log.debug("PedidoRestController.listarPedidoAllPageable...(pageSize: {}, pageNumber: {}, query{}) ", pageSize, pageNumber, query );
       
//      Pageable pageRequest = PageRequest.of(pageNumber, pageSize,
//					Sort.by(direction.toString() ,active.getValue()));
        Pageable pageable = null;
        if(direction.getValue()=="desc") {
        	pageable = PageRequest.of(pageNumber, pageSize,
					Sort.by(active.getValue()).descending());
        } else {
        	pageable = PageRequest.of(pageNumber, pageSize,
					Sort.by(active.getValue()).ascending());
        }
		return pedidoService.findAllPedidoPageable(query, pageable);
	}
	
	
	@GetMapping("/pedidos/estado-pedido")
	@ResponseStatus(HttpStatus.OK)
	public List<EstadoPedido> listarEstadoPedidoAll() {
		return pedidoService.findAllEstadoPedido();
	}
	
	@GetMapping("/pedidos/tipo-pedido")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoPedido> listarTipoPedidoAll() {
		return pedidoService.listarTipoPedidoAll();
	}
	
	
	//@Secured({"ROLE_ADMIN"})
	@PostMapping("/pedidos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> crear(@RequestBody Pedido pedido) {
		Pedido pedidoNew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			pedidoNew = pedidoService.registrarPedido(pedido);
			response.put("mensaje", "El pedido ha sido actualizado con éxito!");
			response.put("pedido", pedidoNew);
		} catch (DataAccessException de) {
			response.put("mensaje", "Error al crear el pedido en la base de datos");
			response.put("err", de.getMessage().concat(": ").concat(de.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(PedidoExceptions pe) {
			response.put("mensaje", pe.getMessage());
			response.put("err", "Error");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//@Secured("ROLE_ADMIN")
	@PutMapping("/pedidos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Pedido pedido, BindingResult result, @PathVariable Long id) {
		Pedido pedidoActualizado = null;
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
			pedidoActualizado = pedidoService.updatePedido(pedido, id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el pedido en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El pedido ha sido actualizado con éxito!");
		response.put("pedido", pedidoActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
