package com.codeyasam.posis.restcontroller;

import java.time.format.DateTimeFormatter;
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
import com.codeyasam.posis.dto.SingleDataResponse;
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
	public SingleDataResponse<EndUserDTO> createUser(@RequestBody EndUser user) throws UserAlreadyExistException {
		SingleDataResponse<EndUserDTO> response = new SingleDataResponse<>();
		user = userService.createUser(user);
		EndUserDTO userDTO = modelMapper.map(user, EndUserDTO.class);
		response.setData(userDTO);
		response.setPrompt("User successfully created.");
		response.setStatus(HttpStatus.CREATED.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<EndUserDTO> updateUser(@RequestBody EndUser user) throws UserNotFoundException {
		SingleDataResponse<EndUserDTO> response = new SingleDataResponse<>();
		EndUserDTO userDTO = convertToDTO(userService.updateUser(user));
		response.setData(userDTO);
		response.setPrompt("User successfully updated.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/disableUser", method=RequestMethod.POST)
	public SingleDataResponse<EndUserDTO> disableUser(@RequestBody EndUser user) throws UserNotFoundException {
		SingleDataResponse<EndUserDTO> response = new SingleDataResponse<>();
		EndUserDTO userDTO = convertToDTO(userService.disableUser(user));
		response.setData(userDTO);
		response.setPrompt("User is now disabled.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@RequestBody EndUser user) throws UserNotFoundException {
		userService.deleteUser(user);
		return new ResponseEntity<>("User with id: " + user.getId() + " is successfully deleted.", HttpStatus.OK);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<EndUserDTO> retrieveUsersBySearch(@RequestParam(value="search", defaultValue="") String text, Pageable pageable) throws PageNotFoundException {
		MultipleDataResponse<EndUserDTO> response = new MultipleDataResponse<>();
		List<EndUserDTO> userList = userService.retrieveInAnyColumn(text, pageable)
			.stream()
			.map(user -> convertToDTO(user))
			.collect(Collectors.toList());
		response.setData(userList);
		response.setTotal(userService.retrieveCountBySpecification(text));
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/searchByUsername", method=RequestMethod.GET)
	public SingleDataResponse<EndUserDTO> retrieveUserByUsername(@RequestParam String username) throws UserNotFoundException {
		SingleDataResponse<EndUserDTO> response = new SingleDataResponse<>();
		EndUserDTO userDTO = convertToDTO(userService.retrieveByUsername(username));
		response.setData(userDTO);
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
		
	private EndUserDTO convertToDTO(EndUser user) {
		EndUserDTO endUserDTO = modelMapper.map(user, EndUserDTO.class);
		endUserDTO.setRoles(user.getRoles());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if (user.getCreatedDate() != null) endUserDTO.setCreatedDate(user.getCreatedDate().format(formatter));
		if (user.getLastModifiedDate() != null) endUserDTO.setLastModifiedDate(user.getLastModifiedDate().format(formatter));
		return endUserDTO;
	}
}
