package com.codeyasam.posis.test.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.service.ProductTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class ProductTypeServiceIntegrationTest {
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@Test
	public void retrieveInSpecifiedColumns() throws PageNotFoundException {
		List<ProductType> productTypeList = productTypeService.retrieveInSpecifiedColumns("   ", new PageRequest(0, 5));
		assertEquals(3, productTypeList.size());
		
		productTypeList = productTypeService.retrieveInSpecifiedColumns("bag", new PageRequest(0, 5));
		assertEquals(1, productTypeList.size());
	}
	
	@Test
	public void retrieveInSpecifiedColumnsWithCaseInsensitivity() throws PageNotFoundException {
		List<ProductType> productTypeList = productTypeService.retrieveInSpecifiedColumns("Bag", new PageRequest(0, 5));
		assertEquals(1, productTypeList.size());
	}
	
	@Test
	public void retrieveCountBySpecification() {
		long count = productTypeService.retrieveCountBySpecification("");
		assertEquals(3, count);
	}
	
	@Test
	@Transactional
	public void createProductType() {
		ProductType productType = new ProductType();
		productTypeService.addProductType(productType);
	}
}
