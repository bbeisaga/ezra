package com.ezra.programandojuntos.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.ezra.programandojuntos.dto.report.FiltrosArrayReporte;
import com.ezra.programandojuntos.dto.report.FiltrosReporte;
import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.enums.SortActivePedido;
import com.ezra.programandojuntos.enums.SortDirection;
import com.ezra.programandojuntos.enums.TypeFile;
import com.ezra.programandojuntos.exceptions.PedidoExceptions;
import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.TipoPedido;
import com.ezra.programandojuntos.models.services.IPedidoService;
import com.ezra.programandojuntos.util.StorageUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" }, originPatterns = {"*"})
@RestController
@RequestMapping("/api")
public class PedidoRestController {
	
	Logger log = LoggerFactory.getLogger(PedidoRestController.class);
	
	@Autowired
	private IPedidoService pedidoService;
	
    @Autowired
    private StorageUtil storageUtil;
	
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
	@GetMapping("/pedidos/{tipoPedidoId}/pageable")
	public Page<Pedido> listarPedidoAllPageable(
								@RequestParam int pageNumber, 
								@RequestParam int pageSize, 
								@RequestParam SortActivePedido active,
								@RequestParam SortDirection direction,
								@RequestParam String query, 
								@PathVariable Long tipoPedidoId) {
		
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
		return pedidoService.findAllPedidoPageable(query,tipoPedidoId, pageable);
	}
	
	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/pedidos/cliente/{clienteId}/pageable")
	public Page<Pedido> listarPedidoClientePageable(
								@RequestParam int pageNumber, 
								@RequestParam int pageSize, 
								@RequestParam SortActivePedido active,
								@RequestParam SortDirection direction,
								@RequestParam String query, 
								@PathVariable Long clienteId) {
		
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
		return pedidoService.findPedidoClientePageable(query,clienteId, pageable);
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
	
	@GetMapping("/pedidos/tipo-pedido/{tipoPedidoId}")
	@ResponseStatus(HttpStatus.OK)
	public TipoPedido getTipoPedido(@PathVariable Long tipoPedidoId) {
		return pedidoService.tipoPedidoById(tipoPedidoId);
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
	
//	@PostMapping("/pedidos-tienda")
//	@ResponseStatus(HttpStatus.CREATED)
//	public ResponseEntity<?> crearPedidoTienda(@RequestBody Pedido pedido) {
//		Pedido pedidoNew = null;
//		Map<String, Object> response = new HashMap<>();
//		try {
//			pedidoNew = pedidoService.registrarPedido(pedido);
//			response.put("mensaje", "El pedido ha sido actualizado con éxito!");
//			response.put("pedido", pedidoNew);
//		} catch (DataAccessException de) {
//			response.put("mensaje", "Error al crear el pedido en la base de datos");
//			response.put("err", de.getMessage().concat(": ").concat(de.getMostSpecificCause().getMessage()));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		} catch(PedidoExceptions pe) {
//			response.put("mensaje", pe.getMessage());
//			response.put("err", "Error");
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
//	}
	
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
	
//	@PostMapping("/pedidos/reporte")
//    public void ejecutarReporte(
//            @Valid @RequestBody FiltrosReporte params, HttpServletResponse response) {
//
//        final String nomReporte = params.getNombre();
//        final String nomArchivo = params.getNombreArchivo();
//        final String tipo = params.getTipo();
//        final var parametros = params.getFiltros();// Map<String, List<Object>>
//        long userId = SiamSession.getInstance().getUsuarioId();
//        Report report = new Report.Builder()
//        				.name(nomReporte)
//        				.type(TypeFile.valueOf(tipo))
//        				.parameter(parametros)
//        				.build();
//        
//        String nomArchivoXX = nomReporte.toLowerCase() + "." + report.getType().getExtension();
//       this.storageUtil.manageDownload(file -> reportService.download(report, file), nomArchivo, response);
//        this.storageUtil.manageDownload(file -> pedidoService.createReportVentas(report), nomArchivoXX, response);
//
//    }
	
	
	@PostMapping("/pedidos/reporte/tipo-pedido")
    public ResponseEntity<?> descargarPedidos(@Valid @RequestBody FiltrosReporte params, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
        Report report = new Report.Builder()
				.name(params.getNombre())
				.tipoPedido(params.getTipoPedido())
				.type(TypeFile.valueOf(params.getTipo()))
				.parameter(params.getFiltros())
				.build();
        
        
        final String  nombreArchivo = report.getName().concat(".xlsx");
        InputStreamResource file = new InputStreamResource(pedidoService.createReportPedidos(report));
        return ResponseEntity.ok()
        		.header( HttpHeaders.CONTENT_DISPOSITION, 
        				"attachment; filename=" + nombreArchivo)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
	
	@PostMapping("/pedidos/download-pdf")
    public ResponseEntity<?> descargarPedidoClientePdf(@Valid  @RequestBody Pedido pedido, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		        
        final String  nombreArchivo = pedido.getCliente().getNomApellRz().replace(" ", "").concat(".pdf");
        InputStreamResource file = new InputStreamResource(pedidoService.downloadOrderClienteToPdf(pedido));
        return ResponseEntity.ok()
        		.header( HttpHeaders.CONTENT_DISPOSITION, 
        				"attachment; filename=" + nombreArchivo)
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(file);
    }
	
	
}
