package com.codeyasam.posis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.ProductType;

public interface ProductTypeRepository extends PagingAndSortingRepository<ProductType, Long> {
	
	public ProductType findByName(String name);
	public List<ProductType> findByNameContaining(String text);
	
	public Page<ProductType> findAll(Specification<ProductType> specification, Pageable pageable);
}
