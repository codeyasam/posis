package com.codeyasam.posis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.ProductType;

public interface ProductTypeRepository extends PagingAndSortingRepository<ProductType, Long> {
	
	public ProductType findByName(String name);
	public ProductType findByNameContaining(String text);
	
}
