package com.codeyasam.posis.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
		inventory = inventoryService.saveInventory(inventory);
		return new ResponseEntity<Inventory>(inventory, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public List<Inventory> retriveByProductId(@PathVariable long id) {
		return inventoryService.retrieveByProductId(id);
	}
	
	@RequestMapping(value="/searchByProductName/{name}", method=RequestMethod.GET)
	public List<Inventory> retrieveByProductName(@PathVariable String name) {
		return inventoryService.retrieveByProductName(name);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<Inventory> retrieveAllInventory(Pageable pageable) {
		return inventoryService.retrieveAllInventory(pageable);
	}
	
}
