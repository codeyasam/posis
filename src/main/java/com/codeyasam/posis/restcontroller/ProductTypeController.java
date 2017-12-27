package com.codeyasam.posis.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.dto.SingleDataResponse;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.service.ProductTypeService;

@RestController
@RequestMapping("/endproduct/type")
public class ProductTypeController {
	
	private ProductTypeService productTypeService;
	
	public ProductTypeController() {
		
	}
	
	@Autowired
	public ProductTypeController(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<ProductType> addProductType(@RequestBody ProductType productType) {
		SingleDataResponse<ProductType> response = new SingleDataResponse<>();
		productType = productTypeService.addProductType(productType);
		response.setData(productType);
		response.setPrompt("Product Category successfully created");
		response.setStatus(HttpStatus.CREATED.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<ProductType> saveProductType(@RequestBody ProductType productType) {
		SingleDataResponse<ProductType> response = new SingleDataResponse<>();
		productType = productTypeService.saveProductType(productType);
		response.setData(productType);
		response.setPrompt("Product Category successfully updated");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<ProductType> retrieveProductTypesBySearch(@RequestParam(value="search", defaultValue="") String text, Pageable pageable) throws PageNotFoundException {
		MultipleDataResponse<ProductType> response = new MultipleDataResponse<>();
		List<ProductType> productTypeList = productTypeService.retrieveInSpecifiedColumns(text, pageable);
		response.setData(productTypeList);
		response.setPrompt("Retrieved searched product categories");
		response.setTotal(productTypeService.retrieveCountBySpecification(text));
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public ProductType retriveByProductTypeName(@PathVariable String name) {
		return productTypeService.retrieveByTypeName(name);
	}
	
	@RequestMapping(value="/searchByProductNameContaining{text}", method=RequestMethod.GET)
	public List<ProductType> retrieveByProductNameContaining(@PathVariable String text) {
		return productTypeService.retrieveByTypeNameContaining(text);
	}
	
	public MultipleDataResponse<ProductType> retrieveAllProduct() {
		MultipleDataResponse<ProductType> response = new MultipleDataResponse<>();
		List<ProductType> productTypeList = productTypeService.retrieveAllProductType();
		response.setData(productTypeList);
		response.setPrompt("Retrieved all product types");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
}
