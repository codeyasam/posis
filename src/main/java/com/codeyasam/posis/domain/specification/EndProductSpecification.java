package com.codeyasam.posis.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.codeyasam.posis.domain.EndProduct;

public class EndProductSpecification {
	
	public static Specification<EndProduct> retrieveInSpecifiedColumns(String text) {
		text = text.trim();
		if (text.isEmpty()) {
			text = "%" +  text + "%";
		}		

		final String finalText = text;
		return (root, query, builder) -> {
			return builder.or(
				builder.like(builder.lower(root.get("name")), finalText.toLowerCase()),
				builder.like(builder.lower(root.get("productType").get("name")), finalText.toLowerCase())
		);};		
	}
	
}
