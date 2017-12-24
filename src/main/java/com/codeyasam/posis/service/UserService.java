package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	private EntityManager entityManager;
	
	public UserService() {
		
	}
	
	@Autowired
	public UserService(UserRepository userRepository, EntityManager entityManager) {
		this.userRepository = userRepository;
		this.entityManager = entityManager;
		passwordEncoder = new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EndUser> foundUser = userRepository.findByUsernameIgnoreCase(username);
		if (!foundUser.isPresent()) {
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		}
		return new UserDetailsImpl(foundUser.get());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public EndUser createUser(EndUser user) throws UserAlreadyExistException {
		Optional<EndUser> foundUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
		if (foundUser.isPresent()) {
			throw new UserAlreadyExistException("User with username: " + user.getUsername() + " already exists.");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public EndUser updateUser(EndUser user) throws UserNotFoundException {
		Optional<EndUser> foundUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
		if (!foundUser.isPresent()) {
			throw new UserNotFoundException("User with username: " + user.getUsername() + " not found");
		}
		
		foundUser.get().setFirstName(user.getFirstName());
		foundUser.get().setLastName(user.getLastName());
		foundUser.get().setRoles(user.getRoles());
		foundUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
		user = userRepository.save(foundUser.get());
		return user;
	}
	
	public EndUser disableUser(EndUser user) throws UserNotFoundException {
		user = retrieveByUsername(user.getUsername());
		user.setStatus("DISABLED");
		return userRepository.save(user);
	}
	
	public void deleteUser(EndUser user) throws UserNotFoundException {
		if (userRepository.findOne(user.getId()) == null) {
			throw new UserNotFoundException("user with id: " + user.getId() + " not found");
		}
		userRepository.delete(user);
	}
	
	public EndUser retrieveByUsername(String username) throws UserNotFoundException {
		Optional<EndUser> foundUser = userRepository.findByUsernameIgnoreCase(username);
		if (!foundUser.isPresent()) {
			throw new UserNotFoundException("User with username: " + username + " not found.");
		}
		return foundUser.get();
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
		int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
		pageable = new PageRequest(pageNumber, pageable.getPageSize(), pageable.getSort());
		List<EndUser> foundUsers = new ArrayList<>();
		Page<EndUser> page = userRepository.findAll(Specifications.where(EndUserSpecification.textInAllColumns(text)), pageable); 
		page.forEach(foundUsers::add);
		if (foundUsers.isEmpty()) {
			throw new PageNotFoundException("No users found on page: " + pageable.getPageNumber() + ", with value of: " + text);
		}
		return foundUsers;
	}
	
	public long retrieveCountBySpecification(String text) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<EndUser> root = criteriaQuery.from(EndUser.class);
		criteriaQuery.select(criteriaBuilder.countDistinct(root));
		Predicate restrictions = EndUserSpecification.textInAllColumns(text).toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}	
}
 