package com.codeyasam.posis.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.codeyasam.posis.domain.security.EndUser;

public class EndUserSpecification {
	
	public static Specification<EndUser> textInAllColumns(String text) {
		final String finalText = text;
		return (root, query, builder) -> builder.or(
				builder.like(root.get("lastName"), finalText),
				builder.like(root.get("firstName"), finalText),
				builder.like(root.get("username"), finalText)
		);
	}
}
