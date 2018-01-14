package com.codeyasam.posis.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.codeyasam.posis.domain.Inventory;

public class InventorySpecification {
	
	public static Specification<Inventory> retrieveInSpecifiedColumns(String text) {
		text = text.trim();
		if (text.isEmpty()) {
			text = "%" + text + "%";
		}
		
		final String finalText = text;
		return (root, query, builder) -> {
			return builder.like(builder.lower(root.get("product").get("name")), finalText.toLowerCase());			
		};
	}
	
}
