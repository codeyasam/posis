package com.codeyasam.posis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.repository.InventoryRepository;

@Service
public class InventoryService {
	
	private InventoryRepository inventoryRepository;
	
	public InventoryService() {
		
	}
	
	@Autowired
	public InventoryService(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}
	
	public void setupInitialInventory(Inventory inventory) {
		this.inventoryRepository.save(inventory);
	}
	
	public void saveInventory(Inventory inventory) {
		this.inventoryRepository.save(inventory);
	}
	
	public void addStockToInventory(long inventoryId, int quantity) {
		Inventory inventory = inventoryRepository.findOne(inventoryId);
		Assert.notNull(inventory, "Inventory object cannot be null");
		int remainingStocks = inventory.getStockQuantity();
		inventory.setStockQuantity(remainingStocks + quantity);
		inventoryRepository.save(inventory);
	}
}
