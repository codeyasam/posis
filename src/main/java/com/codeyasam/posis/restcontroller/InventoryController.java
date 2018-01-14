package com.codeyasam.posis.restcontroller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.dto.InventoryDTO;
import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.dto.SingleDataResponse;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.service.InventoryService;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
	
	private InventoryService inventoryService;
	private ModelMapper modelMapper;
	
	public InventoryController() {
		
	}
	
	@Autowired
	public InventoryController(InventoryService inventoryService, ModelMapper modelMapper) {
		this.inventoryService = inventoryService;
		this.modelMapper = modelMapper;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<InventoryDTO> setupInitialInventory(@RequestBody Inventory inventory) {
		SingleDataResponse<InventoryDTO> response = new SingleDataResponse<>();
		inventory = inventoryService.setupInitialInventory(inventory);
		response.setData(convertToDTO(inventory));
		response.setStatus(HttpStatus.CREATED.value());
		response.setPrompt("Inventory Successfully Created.");
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<InventoryDTO> saveInventory(@RequestBody Inventory inventory) {
		SingleDataResponse<InventoryDTO> response = new SingleDataResponse<>();
		inventory = inventoryService.saveInventory(inventory);
		response.setData(convertToDTO(inventory));
		response.setStatus(HttpStatus.OK.value());
		response.setPrompt("Inventory Successfully Updated.");
		return response;
	}
	
	@RequestMapping(value="/searchById", method=RequestMethod.GET)
	public SingleDataResponse<InventoryDTO> retrieveById(@RequestParam long id) {
		SingleDataResponse<InventoryDTO> response = new SingleDataResponse<>();
		Inventory inventory = inventoryService.retrieveById(id);
		response.setData(convertToDTO(inventory));
		response.setStatus(HttpStatus.OK.value());
		response.setPrompt("Successfully Retrieved Inventory by ID");
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<InventoryDTO> retrieveBySearch(@RequestParam(value="search", defaultValue="") String text, Pageable pageable) throws PageNotFoundException {
		MultipleDataResponse<InventoryDTO> response = new MultipleDataResponse<>();
		List<InventoryDTO> inventoryDTOList = inventoryService.retrieveBySearch(text, pageable)
				.stream()
				.map(inventory -> convertToDTO(inventory))
				.collect(Collectors.toList());
		response.setData(inventoryDTOList);
		response.setTotal(inventoryService.retrieveCountBySpecification(text));
		response.setStatus(HttpStatus.OK.value());
		response.setPrompt("Successfully retrieved list of inventories");
		return response;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public List<Inventory> retriveByProductId(@PathVariable long id) {
		return inventoryService.retrieveByProductId(id);
	}
	
	@RequestMapping(value="/searchByProductName/{name}", method=RequestMethod.GET)
	public List<Inventory> retrieveByProductName(@PathVariable String name) {
		return inventoryService.retrieveByProductName(name);
	}
	
	private InventoryDTO convertToDTO(Inventory inventory) {
		InventoryDTO inventoryDTO = modelMapper.map(inventory, InventoryDTO.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if (inventory.getCreatedDate() != null) inventoryDTO.setCreatedDate(inventory.getCreatedDate().format(formatter));
		if (inventory.getLastModifiedDate() != null) inventoryDTO.setLastModifiedDate(inventory.getLastModifiedDate().format(formatter));
		return inventoryDTO;
	}
}
