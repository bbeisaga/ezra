package com.ezra.programandojuntos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.dto.report.FiltrosArrayReporte;
import com.ezra.programandojuntos.dto.report.FiltrosReporte;
import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.enums.TypeFile;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.services.ICajaUsuarioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" }, originPatterns = {"*"})
@RestController
@RequestMapping("/api")
public class CajaUsuarioRestController {

	@Autowired
	private ICajaUsuarioService cajaUsuarioService;
	
	
	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/cajas/usuarios/{userName}")
	public CajaUsuario getUltimaCajaUsuarioByUserName(@PathVariable String userName) {
		return cajaUsuarioService.findUltimaCajaUsuarioByUserName(userName);
	}
	
	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
//	@GetMapping("/cajas/{cajaId}/usuarios/{userId}")
//	public CajaUsuario getCajaUsuarioByUserIdAndCajaId(@PathVariable Byte cajaId, @PathVariable Long userId ) {
//		return cajaUsuarioService.findCajaUsuarioByUserIdAndCajaId(userId, cajaId);
//	}
	
	@GetMapping("/cajas/{cajaId}/usuarios/{username}")
	public CajaUsuario getCajaUsuarioByUsernameAndCajaId(@PathVariable Byte cajaId, @PathVariable String username ) {
		return cajaUsuarioService.findCajaUsuarioByUsernameAndCajaId(username, cajaId);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/cajas/usuarios")
	public ResponseEntity<?> create(@Valid @RequestBody CajaUsuario cajaUsuario, BindingResult result) {
		//CajaUsuario cajaUsuarioNew = null;
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
			//cajaUsuario.setFechaCreacion(new Date());
			//cajaUsuario.setFechaApertura(new Date());
			//cajaUsuario.setActiva(true);
			cajaUsuarioService.persistCajaUsuario(cajaUsuario);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha guardado con éxito!");
		//response.put("cliente", cajaUsuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	//@Secured("ROLE_ADMIN")
	@PutMapping("/cajas/usuarios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody CajaUsuario cajaUsuario, BindingResult result, @PathVariable Long id) {

		//CajaUsuario cajaUsuarioActual = cajaUsuarioService.findById(id);
		CajaUsuario cajaUsuarioUpdated = null;
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
			cajaUsuarioUpdated = cajaUsuarioService.updateCajaUsuario(cajaUsuario, id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", cajaUsuarioUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/cajas/reporte/cierre-caja")
    public ResponseEntity<?> descargarReporteCierreCaja(@Valid @RequestBody FiltrosArrayReporte params, BindingResult result) {
		
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
        InputStreamResource file = new InputStreamResource(cajaUsuarioService.createReportCajaUsuario(report));
        return ResponseEntity.ok()
        		.header( HttpHeaders.CONTENT_DISPOSITION, 
        				"attachment; filename=" + nombreArchivo)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
	
//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
//	@GetMapping("/clientes/page/{page}")
//	public Page<Cliente> index(@PathVariable Integer page) {
//		Pageable pageable = PageRequest.of(page, 4);
//		return clienteService.findAll(pageable);
//	}
//	
//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
//	@GetMapping("/clientes/{id}")
//	public ResponseEntity<?> show(@PathVariable Long id) {
//		
//		Cliente cliente = null;
//		Map<String, Object> response = new HashMap<>();
//		
//		try {
//			cliente = clienteService.findById(id);
//		} catch(DataAccessException e) {
//			response.put("mensaje", "Error al realizar la consulta en la base de datos");
//			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		if(cliente == null) {
//			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
//		}
//		
//		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
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
//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
//	@GetMapping("/clientes/documentos")
//	public List<TipoDocumento> listarDocumentos(){
//		return clienteService.findAllTipoDocumento();
//	}
//	


//	
//	@Secured("ROLE_ADMIN")
//	@DeleteMapping("/clientes/{id}")
//	public ResponseEntity<?> delete(@PathVariable Long id) {
//		
//		Map<String, Object> response = new HashMap<>();
//		
//		try {
//			Cliente cliente = clienteService.findById(id);
//			String nombreFotoAnterior = cliente.getFoto();
//			
//			uploadService.eliminar(nombreFotoAnterior);
//			
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
//				nombreArchivo = uploadService.copiar(archivo);
//			} catch (IOException e) {
//				response.put("mensaje", "Error al subir la imagen del cliente");
//				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
//				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//			
//			String nombreFotoAnterior = cliente.getFoto();
//			
//			uploadService.eliminar(nombreFotoAnterior);
//						
//			cliente.setFoto(nombreArchivo);
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


}
