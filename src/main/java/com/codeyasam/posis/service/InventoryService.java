package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.domain.specification.InventorySpecification;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.repository.InventoryRepository;

@Service
public class InventoryService {
	
	private InventoryRepository inventoryRepository;
	private EntityManager entityManager;
	
	public InventoryService() {
		
	}
	
	@Autowired
	public InventoryService(InventoryRepository inventoryRepository, EntityManager entityManager) {
		this.inventoryRepository = inventoryRepository;
		this.entityManager = entityManager;
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
	
	public List<Inventory> retrieveBySearch(String text, Pageable pageable) throws PageNotFoundException {
		int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
		pageable = new PageRequest(pageNumber, pageable.getPageSize(), pageable.getSort());				
		List<Inventory> inventoryList = new ArrayList<>();
		Page<Inventory> currentPage = inventoryRepository.findAll(Specifications.where(InventorySpecification.retrieveInSpecifiedColumns(text)), pageable);
		currentPage.forEach(inventoryList::add);
		if (inventoryList.isEmpty()) throw new PageNotFoundException("No inventories found on page" + pageable.getPageNumber() + " with value of: " + text);
		return inventoryList;
	}
	
	public long retrieveCountBySpecification(String text) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Inventory> root = criteriaQuery.from(Inventory.class);
		criteriaQuery.select(criteriaBuilder.countDistinct(root));
		Predicate restrictions = InventorySpecification.retrieveInSpecifiedColumns(text).toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).getSingleResult();
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
