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

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.domain.specification.EndProductSpecification;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.repository.EndProductRepository;

@Service
public class EndProductService {
	
	private EndProductRepository endProductRepository;
	private EntityManager entityManager;
	
	public EndProductService() {
		
	}
	
	@Autowired
	public EndProductService(EndProductRepository endProductRepository, EntityManager entityManager) {
		this.endProductRepository = endProductRepository;
		this.entityManager = entityManager;
	}
	
	public EndProduct addProduct(EndProduct product) {
		return endProductRepository.save(product);
	}
	
	public EndProduct saveProduct(EndProduct product) {
		return endProductRepository.save(product);
	}
	
	//product basic searches
	public EndProduct retrieveById(long id) {
		return endProductRepository.findOne(id);
	}
	
	public List<EndProduct> retrieveBySearch(String text, Pageable pageable) throws PageNotFoundException {
		int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
		pageable = new PageRequest(pageNumber, pageable.getPageSize(), pageable.getSort());		
		List<EndProduct> foundProducts = new ArrayList<>();
		Page<EndProduct> page = endProductRepository.findAll(Specifications.where(EndProductSpecification.retrieveInSpecifiedColumns(text)), pageable);
		page.forEach(foundProducts::add);
		if (foundProducts.isEmpty()) {
			throw new PageNotFoundException("No products found on page: " + pageable.getPageNumber() + ", with value of: " + text);
		}
		return foundProducts;
	}
	
	public long retrieveCountBySpecification(String text) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<EndProduct> root = criteriaQuery.from(EndProduct.class);
		criteriaQuery.select(criteriaBuilder.countDistinct(root));
		Predicate restrictions = EndProductSpecification.retrieveInSpecifiedColumns(text).toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
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
	
	public List<EndProduct> retrieveByProductTypeContaining(String productType, Pageable pageable) throws PageNotFoundException {
		List<EndProduct> productList = endProductRepository.findByProductTypeNameContaining(productType, pageable);
		if (productList.isEmpty()) {
			throw new PageNotFoundException("Page not found exception for products, with value of: " + pageable.getPageNumber());
		}
		return productList;
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
