package com.codeyasam.posis.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.security.Role;
import com.codeyasam.posis.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	private RoleService roleService;
	
	public RoleController() {
		
	}
	
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<Role> retrieveAll() {
		return roleService.retrieveAll();
	}
}
