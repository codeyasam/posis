package com.codeyasam.posis.test.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.domain.PointOfSale;
import com.codeyasam.posis.repository.InventoryRepository;
import com.codeyasam.posis.repository.PointOfSaleRepository;
import com.codeyasam.posis.service.InventoryService;
import com.codeyasam.posis.service.PointOfSaleService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@DataJpaTest
public class PointOfSaleServiceIntegrationTest {

	private PointOfSaleService pointOfSaleService;
	private InventoryService inventoryService;
	
	@Autowired
	private PointOfSaleRepository posRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Before
	public void setup() {
		pointOfSaleService = new PointOfSaleService(posRepository, inventoryRepository);
		inventoryService = new InventoryService(inventoryRepository);
	}
	
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
	
}
