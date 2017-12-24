package com.codeyasam.posis.test.controller;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.domain.PointOfSale;
import com.codeyasam.posis.restcontroller.PointOfSaleController;
import com.codeyasam.posis.service.InventoryService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class PointOfSaleControllerIntegrationTest {
	
	@Autowired
	private PointOfSaleController pointOfSaleController;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Test
	@Transactional
	public void createPointOfSale() {
		Inventory inventory = inventoryService.retrieveById(1);
		int initialStockQty = inventory.getStockQuantity();
		int productQty = 3;
		
		PointOfSale pointOfSale = new PointOfSale();
		pointOfSale.setInventory(inventory);
		pointOfSale.setProductQuantity(productQty);
		pointOfSale.setCreatedDate(LocalDateTime.now());
		
		ResponseEntity<?> response = pointOfSaleController.addPointOfSale(pointOfSale);
		PointOfSale responseObj = (PointOfSale) response.getBody();
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		Assert.assertEquals(responseObj.getInventory().getStockQuantity(), initialStockQty - productQty);
		
	}
}
