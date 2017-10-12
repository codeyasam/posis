package com.codeyasam.posis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public void addProduct(EndProduct product) {
		endProductRepository.save(product);
	}
	
	public void saveProduct(EndProduct product) {
		endProductRepository.save(product);
	}
	
	//product basic searches
	public EndProduct retrieveByName(String name) {
		return endProductRepository.findByName(name);
	}
	
	public List<EndProduct> retrieveByNameContaining(String text) {
		return endProductRepository.findByNameContaining(text);
	}
	
	public List<EndProduct> retrieveByProductType(String productType) {
		return endProductRepository.findByProductTypeName(productType);
	}
}
