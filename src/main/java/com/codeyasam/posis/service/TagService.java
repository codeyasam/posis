package com.codeyasam.posis.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.codeyasam.posis.domain.Tag;
import com.codeyasam.posis.domain.specification.TagSpecification;
import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.repository.TagRepository;

@Service
public class TagService {
	
	private TagRepository tagRepository;
	private EntityManager entityManager;
	
	public TagService() {
		
	}
	
	@Autowired
	public TagService(TagRepository tagRepository, EntityManager entityManager) {
		this.tagRepository = tagRepository;
		this.entityManager = entityManager;
	}
	
	public Tag addTag(Tag tag) {
		return tagRepository.save(tag);
	}
	
	public Tag updateTag(Tag tag) {
		return tagRepository.save(tag);
	}
	
	//searches
	public Tag retrieveById(long id) {
		return tagRepository.findOne(id);
	}
	
	public List<Tag> retrieveInSpecifiedColumns(String text, Pageable pageable) throws PageNotFoundException {
		int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
		pageable = new PageRequest(pageNumber, pageable.getPageSize(), pageable.getSort());
		List<Tag> foundTags = new ArrayList<>();
		Page<Tag> page = tagRepository.findAll(Specifications.where(TagSpecification.searchTextInSpecifiedColumns(text)), pageable);
		page.forEach(foundTags::add);
		if (foundTags.isEmpty()) {
			throw new PageNotFoundException("No tags found on page: " + pageable.getPageNumber() + ", with value of: " + text);
		}
		return foundTags;
	}
	
	public long retrieveCountBySpecification(String text) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Tag> root = criteriaQuery.from(Tag.class);
		criteriaQuery.select(criteriaBuilder.countDistinct(root));
		Predicate restrictions = TagSpecification.searchTextInSpecifiedColumns(text).toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).getSingleResult();		
	}
}
