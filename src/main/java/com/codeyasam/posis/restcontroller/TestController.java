package com.codeyasam.posis.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.domain.PointOfSale;
import com.codeyasam.posis.service.EndProductService;
import com.codeyasam.posis.service.PointOfSaleService;

@RestController
public class TestController {
	
	private PointOfSaleService pointOfSaleService;
	private EndProductService endProductService;
	
	
	public TestController() {
		
	}
	
	@Autowired
	public TestController(PointOfSaleService pointOfSaleService,
			EndProductService endProductService) {
		this.pointOfSaleService = pointOfSaleService;
		this.endProductService  = endProductService;
	}
	
	@RequestMapping("/test")
	public void testTranscational() {
		PointOfSale pointOfSale = new PointOfSale();
		pointOfSale.setInventory(new Inventory(1));
		pointOfSale.setProductQuantity(3);
		pointOfSaleService.addPointOfSale(pointOfSale);
	}
	
	@RequestMapping("/testing")
	public void testing() {
		EndProduct endProduct = endProductService.retrieveByName("tigernu");
		endProduct.getProductType();
	}
	
	@RequestMapping("/endProduct/{name}")
	public EndProduct retrieveEndProduct(@PathVariable String name) {
		return endProductService.retrieveByName(name);
	}
}
