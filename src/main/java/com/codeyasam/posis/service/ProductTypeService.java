package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.domain.specification.ProductTypeSpecification;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.repository.ProductTypeRepository;

@Service
public class ProductTypeService {
	
	private ProductTypeRepository productTypeRepository;
	private EntityManager entityManager;
	
	public ProductTypeService() {
		
	}
	
	@Autowired
	public ProductTypeService(ProductTypeRepository productTypeRepository, EntityManager entityManager) {
		this.productTypeRepository = productTypeRepository;
		this.entityManager = entityManager;
	}
	
	public ProductType addProductType(ProductType productType) {
		return productTypeRepository.save(productType);
	}
	
	public ProductType saveProductType(ProductType productType) {
		return productTypeRepository.save(productType);
	}
	
	//basic searches
	public List<ProductType> retrieveInSpecifiedColumns(String text, Pageable pageable) throws PageNotFoundException {
		int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
		pageable = new PageRequest(pageNumber, pageable.getPageSize(), pageable.getSort());
		List<ProductType> foundProductTypes = new ArrayList<>();
		Page<ProductType> page = productTypeRepository.findAll(Specifications.where(ProductTypeSpecification.textInSpecifiedColumns(text)), pageable); 
		page.forEach(foundProductTypes::add);
		if (foundProductTypes.isEmpty()) {
			throw new PageNotFoundException("No product types found on page: " + pageable.getPageNumber() + ", with value of: " + text);
		}
		return foundProductTypes;
	}
	
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
	
	public long retrieveCountBySpecification(String text) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<ProductType> root = criteriaQuery.from(ProductType.class);
		criteriaQuery.select(criteriaBuilder.countDistinct(root));
		Predicate restrictions = ProductTypeSpecification.textInSpecifiedColumns(text).toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}	
}
