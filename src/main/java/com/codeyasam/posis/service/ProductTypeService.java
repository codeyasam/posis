package com.codeyasam.posis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.repository.ProductTypeRepository;

@Service
public class ProductTypeService {
	
	private ProductTypeRepository productTypeRepository;
	
	public ProductTypeService() {
		
	}
	
	@Autowired
	public ProductTypeService(ProductTypeRepository productTypeRepository) {
		this.productTypeRepository = productTypeRepository;
	}
	
	public void addProductType(ProductType productType) {
		productTypeRepository.save(productType);
	}
	
	public void saveProductType(ProductType productType) {
		productTypeRepository.save(productType);
	}
	
	//basic searches
	public ProductType retrieveByTypeName(String name) {
		return productTypeRepository.findByName(name);
	}
	
	public ProductType retrieveByTypeNameContaining(String text) {
		return productTypeRepository.findByNameContaining(text);
	}
}
