package com.codeyasam.posis.test.service.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.repository.InventoryRepository;
import com.codeyasam.posis.service.InventoryService;


public class InventoryServiceUnitTest {
	
	private InventoryService inventoryService;
	private InventoryRepository inventoryRepositoryMock;
	private EntityManager entityManager;
	
	@Before
	public void setup() {
		inventoryRepositoryMock = mock(InventoryRepository.class);
		entityManager = mock(EntityManager.class);
		inventoryService = new InventoryService(inventoryRepositoryMock, entityManager);
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
	
	@Test
	public void retrieveAll() {
		List<Inventory> inventories = new ArrayList<>();
		inventories.add(new Inventory(1, null, 13, 100, 500));
		inventories.add(new Inventory(2, null, 14, 100, 500));
		inventories.add(new Inventory(3, null, 15, 100, 500));
		
		Page<Inventory> pageableInventory = new PageImpl<Inventory>(inventories);
		when(inventoryRepositoryMock.findAll(any(Pageable.class)))
			.thenReturn(pageableInventory);
		
		List<Inventory> foundInventory = inventoryService.retrieveAllInventory(null);
		assertEquals(inventories.size(), foundInventory.size());
	}
	
	@Test
	public void retrieveFirstIn() {
		Inventory inventory = new Inventory(1, null, 7, 500, 1000);
		when(inventoryRepositoryMock.findFirstByProductIdOrderByCreatedDate(anyLong()))
			.thenReturn(inventory);
		
		Inventory foundInventory = inventoryService.retrieveFirstInByProductId(1);
		assertEquals(foundInventory.getId(), inventory.getId());
	}
	
	@Test
	public void retrieveByProductNameContaining() throws PageNotFoundException {
		List<Inventory> inventories = new ArrayList<>();
		inventories.add(new Inventory(1, null, 13, 100, 500));
		inventories.add(new Inventory(2, null, 14, 100, 500));
		inventories.add(new Inventory(3, null, 15, 100, 500));
		
		when(inventoryRepositoryMock.findByProductNameContaining(anyString()))
			.thenReturn(inventories);
		
		List<Inventory> foundInventories = inventoryService.retrieveByProductNameContaining("i");
		assertEquals(inventories.size(), foundInventories.size());
	}
	
	@Test
	public void retrieveByProductNameWithPagination() throws PageNotFoundException {
		List<Inventory> inventories = new ArrayList<>();
		inventories.add(new Inventory(1, null, 13, 100, 500));
		inventories.add(new Inventory(2, null, 14, 100, 500));
		inventories.add(new Inventory(3, null, 15, 100, 500));
		
		when(inventoryRepositoryMock.findByProductNameContaining(anyString(), any(Pageable.class)))
			.thenReturn(inventories);
		
		List<Inventory> foundInventories = inventoryService.retrieveByProductNameContaining(null, null);
		assertEquals(inventories.size(), foundInventories.size());
	}
}
