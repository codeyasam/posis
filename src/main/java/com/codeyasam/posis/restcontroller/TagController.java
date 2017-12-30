package com.codeyasam.posis.restcontroller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeyasam.posis.domain.Tag;
import com.codeyasam.posis.dto.MultipleDataResponse;
import com.codeyasam.posis.dto.SingleDataResponse;
import com.codeyasam.posis.dto.TagDTO;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.service.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {
	
	private TagService tagService;
	private ModelMapper modelMapper;
	
	public TagController() {
		
	}
	
	@Autowired
	public TagController(TagService tagService, ModelMapper modelMapper) {
		this.tagService = tagService;
		this.modelMapper = modelMapper;
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public SingleDataResponse<TagDTO> addTag(@RequestBody Tag tag) {
		SingleDataResponse<TagDTO> response = new SingleDataResponse<>();
		Tag createdTag = tagService.addTag(tag);
		response.setData(convertToDTO(createdTag));
		response.setPrompt("Tag Successfully Created.");
		response.setStatus(HttpStatus.CREATED.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public SingleDataResponse<TagDTO> updateTag(@RequestBody Tag tag) {
		SingleDataResponse<TagDTO> response = new SingleDataResponse<>();
		Tag updatedTag = tagService.updateTag(tag);
		response.setData(convertToDTO(updatedTag));
		response.setPrompt("Tag Successfully Updated.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/searchById", method=RequestMethod.GET)
	public SingleDataResponse<TagDTO> retrieveById(@RequestParam long id) {
		SingleDataResponse<TagDTO> response = new SingleDataResponse<>();
		Tag foundTag = tagService.retrieveById(id);
		response.setData(convertToDTO(foundTag));
		response.setPrompt("Tag Successfully retrieved by Id.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public MultipleDataResponse<TagDTO> retrieveBySearch(@RequestParam(value="search", defaultValue="") String text, Pageable pageable) throws PageNotFoundException {
		MultipleDataResponse<TagDTO> response = new MultipleDataResponse<>();
		List<TagDTO> tagDTOList = tagService.retrieveInSpecifiedColumns(text, pageable)
				.stream()
				.map(tag -> convertToDTO(tag))
				.collect(Collectors.toList());
		
		response.setData(tagDTOList);
		response.setTotal(tagService.retrieveCountBySpecification(text));
		response.setPrompt("Successfully retrieved by search.");
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	private TagDTO convertToDTO(Tag tag) {
		TagDTO tagDTO = modelMapper.map(tag, TagDTO.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    if (tag.getCreatedDate() != null) tagDTO.setCreatedDate(tag.getCreatedDate().format(formatter));
	    if (tag.getLastModifiedDate() != null) tagDTO.setLastModifiedDate(tag.getLastModifiedDate().format(formatter));
		return tagDTO;
	}
}
