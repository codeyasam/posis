package com.codeyasam.posis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="end_product")
public class EndProduct {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	
	@ManyToOne
	private ProductType productType;

	public EndProduct() {
		
	}
	
	public EndProduct(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
}
