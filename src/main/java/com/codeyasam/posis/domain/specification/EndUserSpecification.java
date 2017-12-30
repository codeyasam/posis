package com.codeyasam.posis.domain.specification;


import javax.persistence.criteria.Expression;

import org.springframework.data.jpa.domain.Specification;

import com.codeyasam.posis.domain.security.EndUser;

public class EndUserSpecification {
	
	public static Specification<EndUser> textInAllColumns(String text) {
		text = text.trim();
		if (text.isEmpty()) {
			text = "%" +  text + "%";
		}
		
		final String finalText = text;
		return (root, query, builder) -> {
			Expression<String> fullNameExpFirstNameFirst = builder.concat(root.get("firstName"), " ");
			fullNameExpFirstNameFirst = builder.concat(fullNameExpFirstNameFirst, root.get("lastName"));
		
			Expression<String> fullNameExpLastNameFirst  = builder.concat(root.get("lastName"), " ");
			fullNameExpLastNameFirst = builder.concat(fullNameExpLastNameFirst, root.get("firstName"));
			
			return builder.or(
				builder.like(builder.lower(fullNameExpFirstNameFirst), finalText.toLowerCase()),
				builder.like(builder.lower(fullNameExpLastNameFirst), finalText.toLowerCase()),
				builder.like(builder.lower(root.get("firstName")), finalText.toLowerCase()),
				builder.like(builder.lower(root.get("lastName")), finalText.toLowerCase()),
				builder.like(builder.lower(root.get("username")), finalText.toLowerCase())
		);};
	}
}
