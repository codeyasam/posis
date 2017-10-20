package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.repository.EndProductRepository;

@Service
public class EndProductService {
	
	private EndProductRepository endProductRepository;
	
	public EndProductService() {
		
	}
	
	@Autowired
	public EndProductService(EndProductRepository endProductRepository) {
		this.endProductRepository = endProductRepository;
	}
	
	public EndProduct addProduct(EndProduct product) {
		return endProductRepository.save(product);
	}
	
	public EndProduct saveProduct(EndProduct product) {
		return endProductRepository.save(product);
	}
	
	//product basic searches
	public EndProduct retrieveByName(String name) {
		return endProductRepository.findByName(name);
	}
	
	public List<EndProduct> retrieveByNameContaining(String text) {
		return endProductRepository.findByNameContaining(text);
	}
	
	public List<EndProduct> retrieveByNameContaining(String name, Pageable pageable) {
		return endProductRepository.findByNameContaining(name, pageable);
	}	

	public List<EndProduct> retrieveByProductType(String productType) {
		return endProductRepository.findByProductTypeName(productType);
	}
	

	
	public List<EndProduct> retrieveAllProduct() {
		List<EndProduct> allProducts = new ArrayList<>();
		endProductRepository.findAll().forEach(allProducts::add);
		return allProducts;
	}
	
	public List<EndProduct> retrieveAllProduct(Pageable pageable) {
		List<EndProduct> allProducts = new ArrayList<>();
		endProductRepository.findAll(pageable).forEach(allProducts::add);
		return allProducts;
	}
}
