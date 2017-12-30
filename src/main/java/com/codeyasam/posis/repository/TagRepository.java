package com.codeyasam.posis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.Tag;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
	
	public Page<Tag> findAll(Specification<Tag> specification, Pageable pageable);
	
}
