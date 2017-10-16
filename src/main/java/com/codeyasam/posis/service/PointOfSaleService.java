package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public PointOfSaleService() {
		
	}
	
	@Autowired
	public PointOfSaleService(PointOfSaleRepository posRepository, InventoryRepository inventoryRepository) {
		this.posRepository = posRepository;
		this.inventoryRepository = inventoryRepository;
	}
	
	@Transactional
	public PointOfSale addPointOfSale(PointOfSale pointOfSale) {
		pointOfSale = savePOS(pointOfSale);
		long inventoryId = pointOfSale.getInventory().getId();
		Inventory inventory = inventoryRepository.findById(inventoryId);
		inventory.setStockQuantity(getRemainingStock(inventory, pointOfSale));
		inventoryRepository.save(inventory);
		return posRepository.findOne(pointOfSale.getId());
	}
	
	public PointOfSale savePOS(PointOfSale pointOfSale) {
		return posRepository.save(pointOfSale);
	}
	
	public PointOfSale retrieveById(long id) {
		return posRepository.findOne(id);
	}
	
	public int getRemainingStock(Inventory inventory, PointOfSale pointOfSale) {
		int stockQuantity = inventory.getStockQuantity();
		int itemQuantity  = pointOfSale.getProductQuantity();
		return stockQuantity - itemQuantity;		
	}
	
	public List<PointOfSale> retrieveAllPointOfSale() {
		List<PointOfSale> allPointSale = new ArrayList<>();
		posRepository.findAll().forEach(allPointSale::add);
		return allPointSale;
	}
	
	public List<PointOfSale> retrieveByProductId(long productId) {
		return posRepository.findByInventoryProductId(productId);
	}
}
