package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Inventory> retrieveByProductId(long id) {
		return inventoryRepository.findByProductId(id);
	}
	
	public Inventory retrieveById(long id) {
		return inventoryRepository.findOne(id);
	}
	
	public List<Inventory> retrieveByProductName(String name) {
		return inventoryRepository.findByProductName(name);
	}
	
	public List<Inventory> retrieveAllInventory() {
		List<Inventory> allInventory = new ArrayList<>();
		inventoryRepository.findAll().forEach(allInventory::add);
		return allInventory;
	}
	
}
