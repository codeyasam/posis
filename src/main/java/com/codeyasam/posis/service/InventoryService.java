package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.exception.PageNotFoundException;
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
	
	public List<Inventory> retrieveByProductNameContaining(String name) {
		return inventoryRepository.findByProductNameContaining(name);
	}
	
	public List<Inventory> retrieveByProductNameContaining(String name, Pageable pageable) throws PageNotFoundException {
		List<Inventory> inventories =  inventoryRepository.findByProductNameContaining(name, pageable);
		if (inventories.isEmpty()) {
			throw new PageNotFoundException("Page not found, with page number of: " + pageable.getPageNumber());
		}
		return inventories;
	}
	
	public List<Inventory> retrieveAllInventory() {
		List<Inventory> allInventory = new ArrayList<>();
		inventoryRepository.findAll().forEach(allInventory::add);
		return allInventory;
	}
	
	public List<Inventory> retrieveAllInventory(Pageable pageable) {
		return inventoryRepository.findAll(pageable).getContent()
				.stream().collect(Collectors.toList());
	}
	
	public Inventory retrieveFirstInByProductId(long productId) {
		return inventoryRepository.findFirstByProductIdOrderByCreatedDate(productId);
	}
	
}
