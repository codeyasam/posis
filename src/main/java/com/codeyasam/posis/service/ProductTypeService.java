package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public ProductType addProductType(ProductType productType) {
		return productTypeRepository.save(productType);
	}
	
	public ProductType saveProductType(ProductType productType) {
		return productTypeRepository.save(productType);
	}
	
	//basic searches
	public ProductType retrieveByTypeName(String name) {
		return productTypeRepository.findByName(name);
	}
	
	public List<ProductType> retrieveByTypeNameContaining(String text) {
		return productTypeRepository.findByNameContaining(text);
	}
	
	public List<ProductType> retrieveAllProductType() {
		List<ProductType> allProductType = new ArrayList<>();
		productTypeRepository.findAll().forEach(allProductType::add);
		return allProductType;
	}
}
