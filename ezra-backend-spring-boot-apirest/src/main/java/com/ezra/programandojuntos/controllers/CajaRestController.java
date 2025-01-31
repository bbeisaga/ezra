package com.ezra.programandojuntos.controllers;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.models.entity.Caja;
import com.ezra.programandojuntos.models.services.ICajaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CajaRestController {

	@Autowired
	private ICajaService cajaService;
	
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/cajas")
	public List<Caja> index() {
		return cajaService.findAll();
	}
	

}
