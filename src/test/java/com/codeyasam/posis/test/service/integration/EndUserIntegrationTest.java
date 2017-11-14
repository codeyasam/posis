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

import com.codeyasam.posis.domain.security.EndUser;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.exception.UserAlreadyExistException;
import com.codeyasam.posis.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class EndUserIntegrationTest {
	
	@Autowired
	private UserService userService;
	
	@Test(expected=UserAlreadyExistException.class)
	public void createUserWithAlreadyExistException() throws UserAlreadyExistException {
		EndUser user = new EndUser();
		user.setFirstName("emman");
		user.setLastName("yasa");
		user.setUsername("codeyasam");
		user.setPassword("secret");
		userService.createUser(user);
	}

	@Test
	public void retrieveUserByFullNameContaining() {
		List<EndUser> foundUsers = userService.retrieveByFullNameContaining("emm", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());
	}
	
	@Test
	public void retrieveInAllColumns() throws PageNotFoundException {
		List<EndUser> foundUsers = userService.retrieveInAnyColumn("emman", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());
		
		foundUsers = userService.retrieveInAnyColumn("%emm%", new PageRequest(0, 5));
		assertEquals(1, foundUsers.size());				
	}
	
	@Test(expected=PageNotFoundException.class)
	public void retrieveInAllColumnsWithPageNotFound() throws PageNotFoundException {
		userService.retrieveInAnyColumn("emm", new PageRequest(0, 5));		
	}
	
}
