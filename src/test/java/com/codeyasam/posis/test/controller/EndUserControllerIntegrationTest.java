package com.codeyasam.posis.test.controller;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.posis.dto.EndUserDTO;
import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.restcontroller.EndUserController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Transactional
public class EndUserControllerIntegrationTest {
	
	@Autowired
	private EndUserController endUserController;
	
	@Test
	public void retrieveAllPaginatedUser() throws PageNotFoundException, UnsupportedEncodingException {
		Pageable pageable = new PageRequest(0, 5);
		MultipleDataResponse<EndUserDTO> response = endUserController.retrieveUsersBySearch("", pageable);
		assertEquals(2, response.getTotal());
	}
	
	@Test
	public void retrieveUserInAllColumnsThatContains() throws PageNotFoundException, UnsupportedEncodingException {
		Pageable pageable = new PageRequest(0, 5);
		MultipleDataResponse<EndUserDTO> response = endUserController.retrieveUsersBySearch("%emm%", pageable);
		assertEquals(1, response.getData().size());
		assertEquals(1, response.getTotal());
	}
	
}
