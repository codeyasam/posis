package com.codeyasam.posis.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.repository.EndProductRepository;
import com.codeyasam.posis.service.EndProductService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EndProductServiceIntegrationTest {
	
	private EndProductService endProductService;
	
	@Autowired
	private EndProductRepository endProductRepository;

	@Before
	public void setup() {
		endProductService = new EndProductService(endProductRepository);
	}
	
	@Test
	public void retrieveAllPaginated() {
		List<EndProduct> endProductList = endProductService.retrieveAllProduct(new PageRequest(0, 3));
		Assert.assertEquals(endProductService.retrieveAllProduct().size(), 4);
		Assert.assertEquals(endProductList.size(), 3);
		
		endProductList = endProductService.retrieveAllProduct(new PageRequest(1, 3));
		Assert.assertEquals(endProductList.size(), 1);
	}
}
