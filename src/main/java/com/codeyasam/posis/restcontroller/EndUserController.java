package com.codeyasam.posis.restcontroller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.security.EndUser;
import com.codeyasam.posis.dto.EndUserDTO;
import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.exception.UserAlreadyExistException;
import com.codeyasam.posis.exception.UserNotFoundException;
import com.codeyasam.posis.service.UserService;

@RestController
@RequestMapping("/users")
public class EndUserController {
	
	private UserService userService;
	private ModelMapper modelMapper;
	
	public EndUserController() {
		
	}
	
	@Autowired
	public EndUserController(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public ResponseEntity<EndUserDTO> createUser(@RequestBody EndUser user) throws UserAlreadyExistException {
		user = userService.createUser(user);
		EndUserDTO userDTO = modelMapper.map(user, EndUserDTO.class);
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<EndUser> updateUser(@RequestBody EndUser user) {
		user = userService.updateUser(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@RequestBody EndUser user) throws UserNotFoundException {
		userService.deleteUser(user);
		return new ResponseEntity<>("User with id: " + user.getId() + " is successfully deleted.", HttpStatus.OK);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<EndUserDTO> retrieveUsersBySearch(@RequestParam(value="search", defaultValue="") String text, Pageable pageable) throws PageNotFoundException, UnsupportedEncodingException {
		MultipleDataResponse<EndUserDTO> response = new MultipleDataResponse<>();
		List<EndUserDTO> userList = userService.retrieveInAnyColumn(text, pageable)
			.stream()
			.map(user -> convertToDTO(user))
			.collect(Collectors.toList());
		response.setData(userList);
		response.setTotal(userService.retrieveCountBySpecification(text));
		return response;
	}
		
	private EndUserDTO convertToDTO(EndUser user) {
		EndUserDTO endUserDTO = modelMapper.map(user, EndUserDTO.class);
		endUserDTO.setRoles(user.getRoles());
		return endUserDTO;
	}
}
