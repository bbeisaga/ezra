package com.ezra.programandojuntos.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezra.programandojuntos.models.entity.GenericosApp;
import com.ezra.programandojuntos.models.services.IGenericosAppService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class GenericosDeProductoRestController {
	
	Logger log = LoggerFactory.getLogger(GenericosDeProductoRestController.class);


	@Autowired
	private IGenericosAppService genericosAppService;
	
//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/genericos-de-producto")
	public List<GenericosApp> lstAllParametros() {
		return genericosAppService.findAlll();
	}
	
}
