package com.codeyasam.posis.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.PointOfSale;

public interface PointOfSaleRepository extends PagingAndSortingRepository<PointOfSale, Long> {
	
	public List<PointOfSale> findByInventoryProductId(long id);
	
}
