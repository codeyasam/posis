package com.codeyasam.posis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.service.ProductTypeService;

@RestController
@RequestMapping("/endproduct/type")
public class ProductTypeController {
	
	private ProductTypeService productTypeService;
	
	public ProductTypeController() {
		
	}
	
	@Autowired
	public ProductTypeController(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public ResponseEntity<?> addProductType(@RequestBody ProductType productType) {
		productType = productTypeService.addProductType(productType);
		return new ResponseEntity<ProductType>(productType, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<?> saveProductType(@RequestBody ProductType productType) {
		productType = productTypeService.saveProductType(productType);
		return new ResponseEntity<ProductType>(productType, HttpStatus.OK);
	}
	
}
