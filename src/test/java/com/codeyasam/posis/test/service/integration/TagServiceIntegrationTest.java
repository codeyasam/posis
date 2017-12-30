package com.codeyasam.posis.test.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codeyasam.posis.domain.Tag;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.service.TagService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class TagServiceIntegrationTest {
	
	@Autowired
	private TagService tagService;
	
	@Test
	public void retrieveBySearch() throws PageNotFoundException {
		List<Tag> tagList = tagService.retrieveInSpecifiedColumns("   ", new PageRequest(1, 5)); 
		assertEquals(2, tagList.size());
	}
	
	@Test
	public void retrieveBySearchWithCaseInsensitivitiy() throws PageNotFoundException {
		List<Tag> tagList = tagService.retrieveInSpecifiedColumns("hazelnut", new PageRequest(0, 5));
		assertEquals(1, tagList.size());
	}
	
	@Test
	@Transactional
	public void createTag() {
		Tag tag = new Tag();
		tag.setName("sample");
		Tag createdTag = tagService.addTag(tag);
		assertEquals(tag.getName(), createdTag.getName());
	}
}
