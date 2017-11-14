package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codeyasam.posis.domain.security.Role;
import com.codeyasam.posis.repository.RoleRepository;

@Service
public class RoleService {

	private RoleRepository roleRepository;
	
	public RoleService() {
		
	}
	
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public List<Role> retrieveAll() {
		List<Role> roles = new ArrayList<>();
		roleRepository.findAll().forEach(roles::add);
		return roles;
	}
}
