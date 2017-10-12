package com.codeyasam.posis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.domain.PointOfSale;
import com.codeyasam.posis.repository.InventoryRepository;
import com.codeyasam.posis.repository.PointOfSaleRepository;

@Service
public class PointOfSaleService {
	
	private PointOfSaleRepository posRepository;
	private InventoryRepository inventoryRepository;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PointOfSaleService() {
		
	}
	
	@Autowired
	public PointOfSaleService(PointOfSaleRepository posRepository, InventoryRepository inventoryRepository) {
		this.posRepository = posRepository;
		this.inventoryRepository = inventoryRepository;
	}
	
	@Transactional
	public void addPointOfSale(PointOfSale pointOfSale) {
		pointOfSale = posRepository.save(pointOfSale);
		long inventoryId = pointOfSale.getInventory().getId();
		Inventory inventory = inventoryRepository.findOne(inventoryId);
		int stockQuantity = inventory.getStockQuantity();
		int itemQuantity  = pointOfSale.getProductQuantity();
		int remainingStock = stockQuantity - itemQuantity;
		inventory.setStockQuantity(remainingStock);
		logger.info("inventory ID: " + inventory.getId());
		logger.info("stockQuantity: " + stockQuantity + ", itemQuantity: " + itemQuantity + ", remainingStock: " + remainingStock);
		inventoryRepository.save(inventory);
	}
}
