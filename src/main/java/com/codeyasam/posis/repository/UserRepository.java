package com.codeyasam.posis.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.security.EndUser;

public interface UserRepository extends PagingAndSortingRepository<EndUser, Long> {
	
	public Optional<EndUser> findByUsername(String username);
	
}
