package com.codeyasam.posis.test.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.service.InventoryService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class InventoryServiceIntegrationTest {
	
	@Autowired
	private InventoryService inventoryService;
	
	@Test
	public void retrieveAllInventory() {
		List<Inventory> pageableInventory = inventoryService.retrieveAllInventory(new PageRequest(0, 3));
		assertEquals(inventoryService.retrieveAllInventory().size(), 4);
		assertEquals(pageableInventory.size(), 3);
		
		pageableInventory = inventoryService.retrieveAllInventory(new PageRequest(1, 3));
		assertEquals(pageableInventory.size(), 1);
	}
	
	@Test
	public void retrieveFirstInByProductId() {
		Inventory inventory = inventoryService.retrieveFirstInByProductId(1);
		assertEquals(inventory.getId(), 2);
	}
	
	@Test
	public void retrieveByProductName() {
		assertEquals(4, inventoryService.retrieveByProductName("tigernu").size());
	}
	
	@Test
	public void retrieveByProductNameWithPagination() throws PageNotFoundException {
		List<Inventory> pageableInventory = inventoryService.retrieveByProductNameContaining("i", new PageRequest(0, 3));
		assertEquals(3, pageableInventory.size());
		
		pageableInventory = inventoryService.retrieveByProductNameContaining("i", new PageRequest(1, 3));
		assertEquals(1, pageableInventory.size());
	}
	
	@Test(expected=PageNotFoundException.class)
	public void retrieveByProductNameWithPageNotFoundException() throws PageNotFoundException {
		inventoryService.retrieveByProductNameContaining("i", new PageRequest(1, 4));
	}
	
	@Test
	@Transactional
	public void updateInventory() {
		Inventory inventory = inventoryService.retrieveById(1);
		EndProduct endProduct = new EndProduct();
		endProduct.setId(1);
		inventory.setProduct(endProduct);
		inventory.setAcquiredPrice(200);
		inventory = inventoryService.saveInventory(inventory);
		assertEquals(200, (int)inventory.getAcquiredPrice());
	}
}