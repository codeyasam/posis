package com.codeyasam.posis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.security.EndUser;

public interface UserRepository extends PagingAndSortingRepository<EndUser, Long> {
	
	public Optional<EndUser> findByUsername(String username);
	public Optional<EndUser> findByUsernameIgnoreCase(String username);
	public List<EndUser> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
	
	public Page<EndUser> findAll(Specification<EndUser> specification, Pageable pageable);
}
