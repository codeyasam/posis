package com.codeyasam.posis.test.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.dto.InventoryDTO;
import com.codeyasam.posis.dto.SingleDataResponse;
import com.codeyasam.posis.restcontroller.InventoryController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class InventoryControllerIntegrationTest {
	
	@Autowired
	private InventoryController inventoryController;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	@Transactional
	public void updateInventory() {
		Inventory inventory = new Inventory();
		inventory.setId(1);
		inventory.setProduct(new EndProduct(1));
		inventory.setAcquiredPrice(200);
		inventory.setStockQuantity(5);
		inventory.setSellingPrice(500);
		HttpEntity<Inventory> request = new HttpEntity<>(inventory);
		SingleDataResponse<InventoryDTO> response = restTemplate.exchange("/inventories/",
				HttpMethod.POST,
				request,
				new ParameterizedTypeReference<SingleDataResponse<InventoryDTO>>() {}).getBody();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(inventory.getSellingPrice(), inventoryController.retrieveById(1).getData().getSellingPrice(), 500);
	}
}
