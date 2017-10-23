package com.codeyasam.posis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<?> addFIFOPointOfSale(@RequestBody PointOfSale pointOfSale) {
		pointOfSale = pointOfSaleService.addFIFOPointOfSale(pointOfSale);
		return new ResponseEntity<PointOfSale>(pointOfSale, HttpStatus.CREATED);
	}	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<PointOfSale> retrieveAllPointOfSale() {
		return pointOfSaleService.retrieveAllPointOfSale();
	}
	
	@RequestMapping(value="/searchByProductId/{productId}", method=RequestMethod.GET)
	public List<PointOfSale> retrieveByProductId(@PathVariable long productId) {
		return pointOfSaleService.retrieveByProductId(productId);
	}
	
	public ResponseEntity<?> addPointOfSale(@RequestBody PointOfSale pointOfSale) {
		pointOfSale = pointOfSaleService.addPointOfSale(pointOfSale);
		return new ResponseEntity<PointOfSale>(pointOfSale, HttpStatus.CREATED);
	}	
}
