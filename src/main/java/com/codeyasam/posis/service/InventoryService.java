package com.codeyasam.posis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Inventory setupInitialInventory(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}
	
	public Inventory saveInventory(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

}
