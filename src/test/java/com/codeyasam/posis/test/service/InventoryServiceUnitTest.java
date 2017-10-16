package com.codeyasam.posis.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.repository.InventoryRepository;
import com.codeyasam.posis.service.InventoryService;


public class InventoryServiceUnitTest {
	
	private InventoryService inventoryService;
	private InventoryRepository inventoryRepositoryMock;
	
	@Before
	public void setup() {
		inventoryRepositoryMock = mock(InventoryRepository.class);
		inventoryService = new InventoryService(inventoryRepositoryMock);
	}
	
	@Test
	public void retrieveById() {
		Inventory inventory = new Inventory();
		inventory.setId(1);
		inventory.setAcquiredPrice(1000);
		inventory.setSellingPrice(15000);
		
		when(inventoryRepositoryMock.findOne(anyLong()))
			.thenReturn(inventory);
		
		int suppliedInventoryId = 1;
		inventory = inventoryService.retrieveById(suppliedInventoryId);
		assertEquals(inventory.getId(), suppliedInventoryId);
	}
	
}
