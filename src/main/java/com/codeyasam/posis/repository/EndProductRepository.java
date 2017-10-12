package com.codeyasam.posis.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.EndProduct;

public interface EndProductRepository extends PagingAndSortingRepository<EndProduct, Long> {
	
	public EndProduct findByName(String name);
	public List<EndProduct> findByNameContaining(String text);
	public List<EndProduct> findByProductTypeName(String name);
	
}
