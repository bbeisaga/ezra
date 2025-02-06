package com.ezra.programandojuntos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.services.IMovimientoCajaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class MovimientoCajaRestController {

	@Autowired
	private IMovimientoCajaService movimientoService;

	
//	@GetMapping("movimientos/tipoMovimientos")
//	@ResponseStatus(HttpStatus.OK)
//	public List<TipoMovimiento> getAllTipoMovimientos() {
//		return movimientoService.lstAllTipoMovimientos();
//	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("movimientos/caja")
	public ResponseEntity<?> createMovimientoCaja(@Valid @RequestBody MovimientoCaja movimientoCaja, BindingResult result) {
		
		MovimientoCaja movimientoNew = null;
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
			movimientoNew = movimientoService.saveMovimiento(movimientoCaja);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert movimientos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Movimiento registrado con éxito!");
		response.put("movimientoCaja", movimientoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
//	
//	@Secured({"ROLE_ADMIN"})
//	@PostMapping("/pedidos")
//	@ResponseStatus(HttpStatus.CREATED)
//	public Pedido crear(@RequestBody Pedido pedido) {
//		return pedidoService.savePedido(pedido);
//	}
//	
//	@Secured("ROLE_ADMIN")
//	@PutMapping("/pedidos/{id}")
//	public ResponseEntity<?> update(@Valid @RequestBody Pedido pedido, BindingResult result, @PathVariable Long id) {
//		Pedido pedidoActualizado = null;
//		Map<String, Object> response = new HashMap<>();
//		if(result.hasErrors()) {
//			List<String> errors = result.getFieldErrors()
//					.stream()
//					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
//					.collect(Collectors.toList());
//			response.put("errors", errors);
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
//		}
//		try {
//			pedidoActualizado = pedidoService.savePedido(pedido);
//
//		} catch (DataAccessException e) {
//			response.put("mensaje", "Error al actualizar el pedido en la base de datos");
//			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		response.put("mensaje", "El pedido ha sido actualizado con éxito!");
//		response.put("pedido", pedidoActualizado);
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
//	}

}
