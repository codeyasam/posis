package com.codeyasam.posis.test.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.codeyasam.posis.controller.PointOfSaleController;
import com.codeyasam.posis.repository.InventoryRepository;
import com.codeyasam.posis.repository.PointOfSaleRepository;
import com.codeyasam.posis.service.InventoryService;
import com.codeyasam.posis.service.PointOfSaleService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@DataJpaTest
public class PointOfSaleControllerIntegrationTest {
	
	@Autowired
	private PointOfSaleController pointOfSaleController;
	
	@Autowired
	private PointOfSaleRepository pointOfSaleRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private MockMvc mockMvc;
	private PointOfSaleService pointOfSaleService;
	private InventoryService inventoryService;
	
	@Before
	public void setup() {
		pointOfSaleService = new PointOfSaleService(pointOfSaleRepository, inventoryRepository);
	}
	
	@Test
	public void createPointOfSale() {
		
	}
}
