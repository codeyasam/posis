package com.codeyasam.posis.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.dto.TagDTO;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.restcontroller.TagController;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TagControllerIntegrationTest {
	
	@Autowired
	private TagController tagController;
	
	@Test
	public void retrieveBySearch() throws PageNotFoundException {
		MultipleDataResponse<TagDTO> response = tagController.retrieveBySearch("", new PageRequest(0, 5));
		assertEquals(2, response.getData().size());
		assertEquals(2, response.getTotal());
	}
}
