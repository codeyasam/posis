package com.codeyasam.posis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.PointOfSale;
import com.codeyasam.posis.service.PointOfSaleService;

@RestController
@RequestMapping("/pointOfSale")
public class PointOfSaleController {
	
	private PointOfSaleService pointOfSaleService;
	
	public PointOfSaleController() {
		
	}
	
	@Autowired
	public PointOfSaleController(PointOfSaleService pointOfSaleService) {
		this.pointOfSaleService = pointOfSaleService;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public ResponseEntity<?> addPointOfSale(@RequestBody PointOfSale pointOfSale) {
		pointOfSale = pointOfSaleService.addPointOfSale(pointOfSale);
		return new ResponseEntity<PointOfSale>(pointOfSale, HttpStatus.CREATED);
	}
}
