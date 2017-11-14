package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codeyasam.posis.domain.security.EndUser;
import com.codeyasam.posis.domain.specification.EndUserSpecification;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.exception.UserAlreadyExistException;
import com.codeyasam.posis.exception.UserNotFoundException;
import com.codeyasam.posis.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserService() {
		
	}
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public Optional<EndUser> retrieveByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EndUser> foundUser = retrieveByUsername(username);
		if (!foundUser.isPresent()) {
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		}
		return new UserDetailsImpl(foundUser.get());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public EndUser createUser(EndUser user) throws UserAlreadyExistException {
		Optional<EndUser> foundUser = retrieveByUsername(user.getUsername());
		if (foundUser.isPresent()) {
			throw new UserAlreadyExistException("User with username: " + user.getUsername() + " already exists.");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public EndUser updateUser(EndUser user) {
		Optional<EndUser> foundUser = retrieveByUsername(user.getUsername());
		if (!foundUser.isPresent()) {
			throw new UsernameNotFoundException("User with username: " + user.getUsername() + " not found");
		}
		return userRepository.save(user);
	}
	
	public void deleteUser(EndUser user) throws UserNotFoundException {
		if (userRepository.findOne(user.getId()) == null) {
			throw new UserNotFoundException("user with id: " + user.getId() + " not found");
		}
		userRepository.delete(user);
	}
	
	public List<EndUser> retrieveAllUser(Pageable pageable) {
		List<EndUser> allUsers = new ArrayList<>();
		userRepository.findAll(pageable).forEach(allUsers::add);
		return allUsers;
	}
	
	public List<EndUser> retrieveByFullNameContaining(String text, Pageable pageable) {
		return userRepository.findByFirstNameContainingOrLastNameContaining(text, text, pageable);
	}
	
	public List<EndUser> retrieveInAnyColumn(String text, Pageable pageable) throws PageNotFoundException {
		List<EndUser> foundUsers = new ArrayList<>();
		Page<EndUser> page = userRepository.findAll(Specifications.where(EndUserSpecification.textInAllColumns(text)), pageable); 
		page.forEach(foundUsers::add);
		if (foundUsers.isEmpty()) {
			throw new PageNotFoundException("No users found on page: " + pageable.getPageNumber() + ", with value of: " + text);
		}
		return foundUsers;
	}

}
 