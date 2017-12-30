package com.codeyasam.posis.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.EndProduct;

public interface EndProductRepository extends PagingAndSortingRepository<EndProduct, Long> {
	
	public EndProduct findByName(String name);
	public List<EndProduct> findByNameContaining(String text);
	public List<EndProduct> findByNameContaining(String text, Pageable pageable);
	public List<EndProduct> findByProductTypeName(String name);
	public List<EndProduct> findByProductTypeNameContaining(String name, Pageable pageable);
	
	public Page<EndProduct> findAll(Specification<EndProduct> specification, Pageable pageable); 
}
