package com.codeyasam.posis.test.controller;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.posis.domain.security.EndUser;
import com.codeyasam.posis.domain.security.Role;
import com.codeyasam.posis.dto.EndUserDTO;
import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.dto.SingleDataResponse;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.exception.UserAlreadyExistException;
import com.codeyasam.posis.restcontroller.EndUserController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class EndUserControllerIntegrationTest {
	
	@Autowired
	private EndUserController endUserController;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
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
	
	@Test
	@Transactional
	public void createUser() throws UserAlreadyExistException {
		EndUser user = new EndUser();
		user.setFirstName("asdf");
		user.setLastName("asdf");
		user.setPassword("asdf");
		user.setUsername("asdf");
		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setId(1);
		roles.add(role);
		user.setRoles(roles);
		SingleDataResponse<EndUserDTO> response = endUserController.createUser(user);
		assertEquals(user.getUsername(), response.getData().getUsername());
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
	
	@Test
	@Transactional
	public void disableUser() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		EndUser user = new EndUser();
		user.setUsername("codeyasam");
		HttpEntity<EndUser> request = new HttpEntity<>(user, headers);	
		SingleDataResponse<EndUserDTO> response = restTemplate.exchange("/users/disableUser",
				HttpMethod.POST,
				request,
				new ParameterizedTypeReference<SingleDataResponse<EndUserDTO>>() {}).getBody();
		System.out.println(response.getStatus());
		assertEquals("codeyasam", response.getData().getUsername());
	}
}
