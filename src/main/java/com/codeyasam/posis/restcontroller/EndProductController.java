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

import com.codeyasam.posis.domain.EndProduct;
import com.codeyasam.posis.dto.EndProductDTO;
import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.dto.SingleDataResponse;
import com.codeyasam.posis.dto.TagDTO;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.service.EndProductService;

@RestController
@RequestMapping("/products")
public class EndProductController {
	
	private EndProductService endProductService;
	private ModelMapper modelMapper;
	
	public EndProductController() {
		
	}
	
	@Autowired
	public EndProductController(EndProductService endProductService, ModelMapper modelMapper) {
		this.endProductService = endProductService;
		this.modelMapper = modelMapper;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<EndProductDTO> addProduct(@RequestBody EndProduct product) {
		SingleDataResponse<EndProductDTO> response = new SingleDataResponse<>();
		product = endProductService.addProduct(product);
		response.setData(convertToDTO(product));
		response.setPrompt("Product Successfully Created.");
		response.setStatus(HttpStatus.CREATED.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<EndProductDTO> saveProduct(@RequestBody EndProduct product) {
		SingleDataResponse<EndProductDTO> response = new SingleDataResponse<>();
		product = endProductService.saveProduct(product);
		response.setData(convertToDTO(product));
		response.setPrompt("Product Successfully Updated");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/searchById", method=RequestMethod.GET)
	public SingleDataResponse<EndProductDTO> retrieveById(@RequestParam long id) {
		SingleDataResponse<EndProductDTO> response = new SingleDataResponse<>();
		EndProduct product = endProductService.retrieveById(id);
		response.setData(convertToDTO(product));
		response.setPrompt("Product Successfully retrieved by Id");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<EndProductDTO> retrieveBySearch(@RequestParam(value="search", defaultValue="") String text, Pageable pageable) throws PageNotFoundException {
		MultipleDataResponse<EndProductDTO> response = new MultipleDataResponse<>();
		List<EndProductDTO> productDTOList = endProductService.retrieveBySearch(text, pageable)
				.stream()
				.map(product -> convertToDTO(product))
				.collect(Collectors.toList());
		
		response.setTotal(endProductService.retrieveCountBySpecification(text));
		response.setData(productDTOList);
		response.setPrompt("Product Successfully Retrieve by search.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/tags", method=RequestMethod.GET)
	public MultipleDataResponse<TagDTO> retrieveTags(@RequestParam long productId) {
		MultipleDataResponse<TagDTO> response = new MultipleDataResponse<>();
		EndProduct product = endProductService.retrieveById(productId);
		List<TagDTO> tagDTOList = product.getProductTags()
				.stream()
				.map(tag -> modelMapper.map(tag, TagDTO.class))
				.collect(Collectors.toList());
		response.setData(tagDTOList);
		response.setPrompt("Successfully retrieved owned tags");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public EndProduct retrieveProductByName(@PathVariable String name) {
		return endProductService.retrieveByName(name);
	}
	
	@RequestMapping(value="/searchByNameContaining", method=RequestMethod.GET)
	public List<EndProduct> retrieveByNameContaining(@RequestParam String text, Pageable pageable) {
		return endProductService.retrieveByNameContaining(text, pageable);
	}
	
	@RequestMapping(value="/searchByProductType", method=RequestMethod.GET)
	public List<EndProduct> retrieveByProductType(@RequestParam String type) {
		return endProductService.retrieveByProductType(type);
	}	
	
	private EndProductDTO convertToDTO(EndProduct product) {
		EndProductDTO endProductDTO = modelMapper.map(product, EndProductDTO.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if (product.getCreatedDate() != null) endProductDTO.setCreatedDate(product.getCreatedDate().format(formatter));
		if (product.getLastModifiedDate() != null) endProductDTO.setLastModifiedDate(product.getLastModifiedDate().format(formatter));
		return endProductDTO;
	}
	
}
