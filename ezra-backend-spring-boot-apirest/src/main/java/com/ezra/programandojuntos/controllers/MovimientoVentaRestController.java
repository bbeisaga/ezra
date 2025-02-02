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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.models.entity.MovimientoVenta;
import com.ezra.programandojuntos.models.entity.TipoMovimiento;
import com.ezra.programandojuntos.models.entity.TipoPago;
import com.ezra.programandojuntos.models.services.IMovimientoVentaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class MovimientoVentaRestController {

	@Autowired
	private IMovimientoVentaService movimientoService;

	@GetMapping("movimientos/tipoPagos")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoPago> getAllTipoPagos() {
		return movimientoService.lstAllTipoPagos();
	}
	
	@GetMapping("movimientos/tipoMovimientos")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoMovimiento> getAllTipoMovimientos() {
		return movimientoService.lstAllTipoMovimientos();
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("movimientos")
	public ResponseEntity<?> create(@Valid @RequestBody MovimientoVenta movimientoVenta, BindingResult result) {
		
		MovimientoVenta movimientoNew = null;
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
			movimientoNew = movimientoService.saveMovimiento(movimientoVenta);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert movimientos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Movimiento registrado con éxito!");
		response.put("movimientoVenta", movimientoNew);
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
