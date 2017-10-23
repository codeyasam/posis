package com.codeyasam.posis.test.service.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.service.EndProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class EndProductServiceIntegrationTest {
	
	@Autowired
	private EndProductService endProductService;
	
	@Test
	public void retrieveAllPaginated() {
		List<EndProduct> endProductList = endProductService.retrieveAllProduct(new PageRequest(0, 3));
		Assert.assertEquals(endProductService.retrieveAllProduct().size(), 4);
		Assert.assertEquals(endProductList.size(), 3);
		
		endProductList = endProductService.retrieveAllProduct(new PageRequest(1, 3));
		Assert.assertEquals(endProductList.size(), 1);
	}
	
	@Test
	public void retriveByNameContainingPaginated() {
		List<EndProduct> products = endProductService.retrieveByNameContaining("i", new PageRequest(0, 2));
		Assert.assertEquals(endProductService.retrieveByNameContaining("i").size(), 3);
		
		Assert.assertEquals(products.size(), 2);
		products = endProductService.retrieveByNameContaining("i", new PageRequest(1, 2));
		Assert.assertEquals(products.size(), 1);
	}
	
	@Test
	public void retrieveByProductTypeContainingPaginated() throws PageNotFoundException {
		List<EndProduct> products = endProductService.retrieveByProductTypeContaining("ba", new PageRequest(0, 2));
		Assert.assertEquals(endProductService.retrieveByProductType("bag").size(), 4);
		
		Assert.assertEquals(products.size(), 2);
		products = endProductService.retrieveByProductTypeContaining("ba", new PageRequest(1, 2));
		Assert.assertEquals(2, products.size());
	}	
	
	@Test(expected=PageNotFoundException.class)
	public void retrieveByProductTypeContainingPageNotFoundException() throws PageNotFoundException {
		endProductService.retrieveByProductTypeContaining("ba", new PageRequest(3, 2));
	}
}
