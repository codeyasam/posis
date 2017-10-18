package com.codeyasam.posis.test.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.repository.EndProductRepository;
import com.codeyasam.posis.repository.ProductTypeRepository;
import com.codeyasam.posis.service.EndProductService;

public class EndProductServiceTest {
	
	private EndProductService endProductService;
	private EndProductRepository endProductRepositoryMock;
	private ProductTypeRepository productTypeRepositoryMock;
	
	@Before
	public void setUp() {
		endProductRepositoryMock = Mockito.mock(EndProductRepository.class);
		productTypeRepositoryMock = Mockito.mock(ProductTypeRepository.class);
		endProductService = new EndProductService(endProductRepositoryMock);
	
		setupMockedEndProductType();
		setupMockedEndProduct();
	}
	
	public void setupMockedEndProductType() {
		ProductType productType = new ProductType();
		productType.setId(1);
		productType.setProductType("sampleProductType");
		
		Mockito.when(productTypeRepositoryMock.findOne(Mockito.anyLong()))
			.thenReturn(productType);
	}
	
	public void setupMockedEndProduct() {
		EndProduct endProduct = new EndProduct();
		endProduct.setId(1);
		endProduct.setName("New Product");
		endProduct.setProductType(productTypeRepositoryMock.findOne((long) 1));
		
		Mockito.when(endProductRepositoryMock.save(Mockito.any(EndProduct.class)))
			.thenReturn(endProduct);
	}
	
	
	@Test
	public void addProduct() {
		EndProduct endProduct = endProductService.addProduct(new EndProduct());
		Assert.assertEquals(endProduct.getName(), "New Product");
		Assert.assertEquals(endProduct.getProductType().getProductType(), "sampleProductType");
	}
	
	

}
