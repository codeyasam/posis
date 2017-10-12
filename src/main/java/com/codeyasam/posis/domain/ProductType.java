package com.codeyasam.posis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_type")
public class ProductType {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductType() {
		return name;
	}

	public void setProductType(String productType) {
		this.name = productType;
	}

}
