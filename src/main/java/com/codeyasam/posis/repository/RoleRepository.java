package com.codeyasam.posis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.security.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
	
}
