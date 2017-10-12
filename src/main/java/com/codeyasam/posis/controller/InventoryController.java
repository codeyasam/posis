package com.codeyasam.posis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.Inventory;
import com.codeyasam.posis.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	
	private InventoryService inventoryService;
	
	public InventoryController() {
		
	}
	
	@Autowired
	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public ResponseEntity<?> setupInitialInventory(@RequestBody Inventory inventory) {
		inventory = inventoryService.setupInitialInventory(inventory);
		return new ResponseEntity<Inventory>(inventory, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<?> saveInventory(@RequestBody Inventory inventory) {
		return new ResponseEntity<Inventory>(inventory, HttpStatus.OK);
	}
	
}
