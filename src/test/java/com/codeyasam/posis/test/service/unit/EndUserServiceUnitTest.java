package com.codeyasam.posis.test.service.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.codeyasam.posis.domain.security.EndUser;
import com.codeyasam.posis.exception.UserAlreadyExistException;
import com.codeyasam.posis.repository.UserRepository;
import com.codeyasam.posis.service.UserService;

public class EndUserServiceUnitTest {
	
	private UserService userService;
	private UserRepository userRepository;
	private EntityManager entityManager;
	private Optional<EndUser> foundUser;
	private EndUser user;
	
	@Before
	public void setup() {
		userRepository = mock(UserRepository.class);
		entityManager = mock(EntityManager.class);
		userService = new UserService(userRepository, entityManager);
		foundUser = Optional.empty();
		setupUser();
	}
	
	public void setupUser() {
		user = new EndUser();
		user.setFirstName("emman");
		user.setLastName("yasa");
		user.setUsername("codeyasam");
		user.setPassword("secret");
	}
	
	@Test
	public void createUser() throws UserAlreadyExistException {
		when(userService.retrieveByUsername(anyString())).thenReturn(foundUser);		
		
		EndUser newUser = new EndUser();
		newUser.setFirstName("emman");
		newUser.setLastName("yasa");
		newUser.setUsername("codeyasam");
		newUser.setPassword("secret");
		when(userService.createUser(newUser)).thenReturn(user);
		
		EndUser createdUser = userService.createUser(newUser);
		assertEquals(newUser.getFirstName(), createdUser.getFirstName());
	}
	
}
