package com.codeyasam.posis.test.service.integration;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.domain.PointOfSale;
import com.codeyasam.posis.service.InventoryService;
import com.codeyasam.posis.service.PointOfSaleService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class PointOfSaleServiceIntegrationTest {

	@Autowired
	private PointOfSaleService pointOfSaleService;
	
	@Autowired
	private InventoryService inventoryService;
		
	@Test
	public void createPointOfSale() {
		Inventory inventory = inventoryService.retrieveById(1);
		int initialStockQuantity = inventory.getStockQuantity();
		PointOfSale pointOfSale = new PointOfSale();
		pointOfSale.setInventory(inventory);
		pointOfSale.setProductQuantity(3);
		pointOfSale.setCreatedDate(LocalDateTime.now());
		pointOfSale = pointOfSaleService.addPointOfSale(pointOfSale);
		
		int remainingStockQuantity = pointOfSale.getInventory().getStockQuantity();
		assertEquals(remainingStockQuantity, initialStockQuantity - pointOfSale.getProductQuantity());
	}
	
	@Test
	public void createFIFOPointOfSale() {
		Inventory expectedInventory = inventoryService.retrieveById(2);
		int initialStockQuantity = expectedInventory.getStockQuantity();
		
		Inventory inventory = inventoryService.retrieveById(1);
		PointOfSale pointOfSale = new PointOfSale();
		pointOfSale.setInventory(inventory);
		pointOfSale.setProductQuantity(3);
		pointOfSale.setCreatedDate(LocalDateTime.now());
		pointOfSale = pointOfSaleService.addFIFOPointOfSale(pointOfSale);
		
		int remainingStockQuantity = pointOfSale.getInventory().getStockQuantity();
		assertEquals(pointOfSale.getInventory().getId(), 2);
		assertEquals(remainingStockQuantity, initialStockQuantity - pointOfSale.getProductQuantity());
	}
	
}
