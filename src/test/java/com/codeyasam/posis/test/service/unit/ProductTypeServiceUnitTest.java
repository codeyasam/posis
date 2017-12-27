package com.codeyasam.posis.test.service.unit;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.repository.ProductTypeRepository;
import com.codeyasam.posis.service.ProductTypeService;

public class ProductTypeServiceUnitTest {
	
	private ProductTypeService productTypeService;
	private ProductTypeRepository productTypeRepositoryMock;
	private EntityManager entityManager;
	
	@Before
	public void setUp() {
		productTypeRepositoryMock = Mockito.mock(ProductTypeRepository.class);
		entityManager = Mockito.mock(EntityManager.class);
		productTypeService = new ProductTypeService(productTypeRepositoryMock, entityManager);
		ProductType productType = new ProductType();
		productType.setId(1);
		productType.setProductType("New Product!");
		
		Mockito.when(productTypeRepositoryMock.save(Mockito.any(ProductType.class)))
			.thenReturn(productType);
	}
	
	@Test
	public void addProductType() {
		long id = 1;
		String productName = "New Product!";
		ProductType productType = new ProductType();
		productType.setId(1);
		productType.setProductType(productName);
		productType = productTypeService.addProductType(productType);
		Assert.assertEquals(productType.getId(), id);
		Assert.assertEquals(productType.getProductType(), productName);
	}
}
