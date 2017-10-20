package com.codeyasam.posis.test.service.unit;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.repository.EndProductRepository;
import com.codeyasam.posis.repository.ProductTypeRepository;
import com.codeyasam.posis.service.EndProductService;

public class EndProductServiceUnitTest {
	
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
	
	public void setupMockedProductList() {
		List<EndProduct> productList = new ArrayList<>();
		productList.add(new EndProduct(1, "iasdf", null));
		productList.add(new EndProduct(2, "qwier", null));
		productList.add(new EndProduct(3, "zcvfi", null));
		
		Mockito.when(endProductRepositoryMock.findByNameContaining(Mockito.anyString(), Mockito.any(Pageable.class)))
			.thenReturn(productList);
	}
	
	
	@Test
	public void addProduct() {
		EndProduct endProduct = endProductService.addProduct(new EndProduct());
		Assert.assertEquals(endProduct.getName(), "New Product");
		Assert.assertEquals(endProduct.getProductType().getProductType(), "sampleProductType");
	}
	
	@Test
	public void findByNameContainingWithPagination() {
		setupMockedProductList();
		List<EndProduct> productList = endProductService.retrieveByNameContaining(null, null);
		Assert.assertEquals(productList.size(), 3);
	}

}
