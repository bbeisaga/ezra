package com.ezra.programandojuntos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

import com.ezra.programandojuntos.models.entity.MovimientoCaja;
import com.ezra.programandojuntos.models.entity.TipoMovimientoCaja;
import com.ezra.programandojuntos.models.services.IMovimientoCajaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class MovimientoCajaRestController {

	@Autowired
	private IMovimientoCajaService movimientoCajaService;

	
	@GetMapping("movimientos/tipoMovimientosCaja")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoMovimientoCaja> getAllTipoMovimientosCaja() {
		return movimientoCajaService.lstAllTipoMovimientosCaja();
	}
	
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
			movimientoNew = movimientoCajaService.saveMovimiento(movimientoCaja);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert movimientos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Movimiento registrado con éxito!");
		response.put("movimientoCaja", movimientoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
