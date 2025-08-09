package com.ezra.programandojuntos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.dto.report.FiltrosArrayReporte;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.enums.TypeFile;
import com.ezra.programandojuntos.exceptions.MovimientoExceptions;
import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.TipoMovimientoPedido;
import com.ezra.programandojuntos.models.entity.TipoPago;
import com.ezra.programandojuntos.models.services.IMovimientoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" }, originPatterns = {"*"})
@RestController
@RequestMapping("/api")
public class MovimientoRestController {

	@Autowired
	private IMovimientoService movimientoService;

	@GetMapping("movimientos/tipoPagos")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoPago> getAllTipoPagos() {
		return movimientoService.lstAllTipoPagos();
	}
	
	@GetMapping("movimientos/tipoMovimientosPedido")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoMovimientoPedido> getAllTipoMovimientosPedido() {
		return movimientoService.lstAllTipoMovimientosPedido();
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("movimientos")
	public ResponseEntity<?> create(@Valid @RequestBody Movimiento movimiento, BindingResult result) {
		
		Movimiento movimientoNew = null;
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
			movimientoNew = movimientoService.saveMovimiento(movimiento);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert movimientos en la base de datos");
			response.put("err", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(MovimientoExceptions me) {
			response.put("mensaje", me.getMessage());
			response.put("err", "Error");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		response.put("mensaje", "Movimiento registrado con éxito!");
		response.put("movimiento", movimientoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/movimientos/reporte/mov-en-caja")
    public ResponseEntity<?> descargarReporteMovimientoEnCaja(@Valid @RequestBody FiltrosArrayReporte params, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
        ReportArray report = new ReportArray.Builder()
				.name(params.getNombre())
			//	.tipoPedido(params.getTipoPedido())
				.type(TypeFile.valueOf(params.getTipo()))
				.parameter(params.getFiltros())
				.build();
        
        
        final String  nombreArchivo = report.getName().concat(".xlsx");
        InputStreamResource file = new InputStreamResource(movimientoService.createReportMovimientoEnCaja(report));
        return ResponseEntity.ok()
        		.header( HttpHeaders.CONTENT_DISPOSITION, 
        				"attachment; filename=" + nombreArchivo)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
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
