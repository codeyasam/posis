package com.codeyasam.posis.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public EndProduct retrieveProductByName(@PathVariable String name) {
		return endProductService.retrieveByName(name);
	}
	
	@RequestMapping(value="/searchByNameContaining", method=RequestMethod.GET)
	public List<EndProduct> retrieveByNameContaining(@RequestParam String text, Pageable pageable) {
		return endProductService.retrieveByNameContaining(text, pageable);
	}
	
	@RequestMapping(value="/searchByProductType", method=RequestMethod.GET)
	public List<EndProduct> retrieveByProductType(@RequestParam String type) {
		return endProductService.retrieveByProductType(type);
	}
		
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<EndProduct> retrieveAllProductType(Pageable pageable) {
		return endProductService.retrieveAllProduct(pageable);
	}	
	
}
