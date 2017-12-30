package com.codeyasam.posis.test.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.dto.TagDTO;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.restcontroller.TagController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TagControllerIntegrationTest {
	
	@Autowired
	private TagController tagController;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void retrieveBySearch() throws PageNotFoundException {
		MultipleDataResponse<TagDTO> response = tagController.retrieveBySearch("", new PageRequest(0, 5));
		assertEquals(2, response.getData().size());
		assertEquals(2, response.getTotal());
	}
	
	@Test
	public void retrieveBySearchUsingRestTemplate() throws PageNotFoundException {
		MultipleDataResponse<TagDTO> response = restTemplate.exchange("/tags/?size=5&sort=id",
													HttpMethod.GET,
													null,
													new ParameterizedTypeReference<MultipleDataResponse<TagDTO>>() {}).getBody();
		assertEquals(2, response.getTotal());
	}
}
