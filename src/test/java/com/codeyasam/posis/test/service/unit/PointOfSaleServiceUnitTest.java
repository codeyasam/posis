package com.codeyasam.posis.test.service.unit;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.domain.PointOfSale;
import com.codeyasam.posis.repository.InventoryRepository;
import com.codeyasam.posis.repository.PointOfSaleRepository;
import com.codeyasam.posis.service.InventoryService;
import com.codeyasam.posis.service.PointOfSaleService;


public class PointOfSaleServiceUnitTest {
	
	private PointOfSaleService pointOfSaleService;
	private PointOfSaleRepository pointOfSaleRepositoryMock;
	private InventoryService inventoryService;
	private InventoryRepository inventoryRepositoryMock;
	private EntityManager entityManager;
	
	@Before
	public void setUp() {
		pointOfSaleRepositoryMock = Mockito.mock(PointOfSaleRepository.class);
		inventoryRepositoryMock   = Mockito.mock(InventoryRepository.class);
		entityManager = Mockito.mock(EntityManager.class);
		pointOfSaleService = new PointOfSaleService(pointOfSaleRepositoryMock, inventoryRepositoryMock);
		inventoryService = new InventoryService(inventoryRepositoryMock, entityManager);
		setupInventory();
		setupMockedPos();
	}
	
	private void setupInventory() {
		Inventory inventory = new Inventory();
		inventory.setId(1);
		inventory.setAcquiredPrice(1000);
		inventory.setSellingPrice(1500);
		inventory.setStockQuantity(10);
		
		Mockito.when(inventoryRepositoryMock.findOne(Mockito.anyLong()))
			.thenReturn(inventory);
		
	}
	
	private void setupMockedPos() {
		PointOfSale pointOfSale = new PointOfSale();
		pointOfSale.setId(1);
		pointOfSale.setInventory(inventoryService.retrieveById(1));
		pointOfSale.setProductQuantity(15);
		
		Mockito.when(pointOfSaleRepositoryMock.findOne(Mockito.anyLong()))
			.thenReturn(pointOfSale);
		
		Mockito.when(pointOfSaleRepositoryMock.save(Mockito.any(PointOfSale.class)))
			.thenReturn(pointOfSale);
	}
	
	@Test
	public void retrieveById() {
		long suppliedPointOfSaleId = 1;
		PointOfSale pointOfSale = pointOfSaleService.retrieveById(suppliedPointOfSaleId);
		Assert.assertEquals(pointOfSale.getId(), suppliedPointOfSaleId);
	}
	
	@Test
	public void getRemainingStock() {
		Inventory inventory = inventoryService.retrieveById(1);
		PointOfSale pointOfSale = pointOfSaleService.retrieveById(1);
		
		int remainingStock = pointOfSaleService.getRemainingStock(inventory, pointOfSale);
		Assert.assertEquals(remainingStock, inventory.getStockQuantity() - pointOfSale.getProductQuantity());
	}
	
	
}
