package com.codeyasam.posis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.service.EndProductService;

@RestController
@RequestMapping("/endproduct")
public class EndProductController {
	
	private EndProductService endProductService;
	
	public EndProductController() {
		
	}
	
	@Autowired
	public EndProductController(EndProductService endProductService) {
		this.endProductService = endProductService;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public ResponseEntity<?> addProduct(@RequestBody EndProduct product) {
		product = endProductService.addProduct(product);
		return new ResponseEntity<EndProduct>(product, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<?> saveProduct(@RequestBody EndProduct product) {
		product = endProductService.saveProduct(product);
		return new ResponseEntity<EndProduct>(product, HttpStatus.OK);
	}
	
}
