package com.codeyasam.posis.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.Inventory;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long> {
	
	public List<Inventory> findByProductId(long id);
	public List<Inventory> findByProductName(String name);
	public List<Inventory> findByProductNameContaining(String name);
	public List<Inventory> findByProductNameContaining(String name, Pageable pageable);
	public Inventory findById(long id);
	public Inventory findFirstByProductIdOrderByCreatedDate(long id);
}
