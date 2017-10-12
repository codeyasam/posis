package com.codeyasam.posis.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.Inventory;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long> {
	
	public List<Inventory> findByProductId(long id);
	
}
