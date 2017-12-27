package com.codeyasam.posis.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.codeyasam.posis.domain.ProductType;

public class ProductTypeSpecification {
	
	public static Specification<ProductType> textInSpecifiedColumns(String text) {
		text = text.trim();
		if (text.isEmpty()) {
			text = "%" +  text + "%";
		}		

		final String finalText = text;
		return (root, query, builder) -> {
			return builder.or(
				builder.like(root.get("name"), finalText)
		);};		
	}
	
}
