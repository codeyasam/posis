package com.codeyasam.posis.test.service.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.posis.domain.security.EndUser;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.exception.UserAlreadyExistException;
import com.codeyasam.posis.exception.UserNotFoundException;
import com.codeyasam.posis.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class EndUserIntegrationTest {
	
	@Autowired
	private UserService userService;
	
	@Test(expected=UserAlreadyExistException.class)
	@Transactional
	public void createUserWithAlreadyExistException() throws UserAlreadyExistException {
		EndUser user = new EndUser();
		user.setFirstName("emman");
		user.setLastName("yasa");
		user.setUsername("codeyasam");
		user.setPassword("secret");
		userService.createUser(user);
	}
	
	@Test(expected=UserAlreadyExistException.class)
	@Transactional
	public void createUserWithAlreadyExistExceptionIgnoreCase() throws UserAlreadyExistException {
		EndUser user = new EndUser();
		user.setFirstName("emman");
		user.setLastName("yasa");
		user.setUsername("CODEYASAM");
		user.setPassword("secret");
		userService.createUser(user);		
	}
	
	@Test
	@Transactional
	public void updateUser() throws UserNotFoundException {
		EndUser user = new EndUser();
		user.setFirstName("emman");
		user.setLastName("yasa");
		user.setUsername("CODEYASAM");
		user.setPassword("secret");
		user = userService.updateUser(user);
		assertNotEquals("secret", user.getPassword());
		assertEquals(1, user.getId());
	}

	@Test
	public void retrieveUserByFullNameContaining() {
		List<EndUser> foundUsers = userService.retrieveByFullNameContaining("emm", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());
	}
	
	@Test
	public void retrieveInAllColumns() throws PageNotFoundException {	
		List<EndUser> foundUsers = userService.retrieveInSpecifiedColumns("%emm%", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());	
	}
	
	@Test
	public void retrieveUserBySearchWithCaseInsensitivity() throws PageNotFoundException {
		List<EndUser> foundUsers = userService.retrieveInSpecifiedColumns("%Emm%", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());
	}
 	
	@Test
	public void retrieveCountInAllColumns() {
		long count = userService.retrieveCountBySpecification("%emm%");
		assertEquals(1, count);
	}
	
	@Test(expected=PageNotFoundException.class)
	public void retrieveInAllColumnsWithPageNotFound() throws PageNotFoundException {
		userService.retrieveInSpecifiedColumns("emm", new PageRequest(0, 5));		
	}
	
	@Test
	public void retrieveByFullName() throws PageNotFoundException {
		List<EndUser> foundUsers = userService.retrieveInSpecifiedColumns("emman yasa", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());
	}
	
	@Test
	public void retrieveCountByFullName() {
		long count = userService.retrieveCountBySpecification("emman yasa");
		assertEquals(1, count);
	}
	
	@Test
	public void retrieveByFirstName() throws PageNotFoundException {
		List<EndUser> foundUsers = userService.retrieveInSpecifiedColumns("emman", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());		
	}
	
	@Test
	public void retrieveCountByFirstName() {
		long count = userService.retrieveCountBySpecification("emman");
		assertEquals(1, count);
	}
	
	@Test
	public void retrieveByLastName() throws PageNotFoundException {
		List<EndUser> foundUsers = userService.retrieveInSpecifiedColumns("yasa", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());		
	}
	
	@Test
	public void retrieveCountByLastName() {
		long count = userService.retrieveCountBySpecification("yasa");
		assertEquals(1, count);
	}
	
	@Test
	public void retrieveAll() throws PageNotFoundException {
		List<EndUser> foundUsers = userService.retrieveInSpecifiedColumns("", new PageRequest(0, 5));
		assertEquals(2, foundUsers.size());
	}
	
	@Test
	public void retrieveCountAll() {
		long count = userService.retrieveCountBySpecification("");
		assertEquals(2, count);
	}
	
	@Test
	public void retrieveByUsername() throws UserNotFoundException {
		EndUser user = userService.retrieveByUsername("codeyasam");
		assertEquals("codeyasam", user.getUsername());
	}
	
	@Test(expected=UserNotFoundException.class)
	public void retrieveByUsernameWithUserNotFoundException() throws UserNotFoundException {
		userService.retrieveByUsername("randomq341");
	}
}
