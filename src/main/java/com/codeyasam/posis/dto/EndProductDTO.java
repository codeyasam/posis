package com.codeyasam.posis.dto;

import java.util.List;

import com.codeyasam.posis.domain.ProductType;
import com.codeyasam.posis.domain.Tag;

public class EndProductDTO {
	
	private long id;
	private String name;
	private ProductType productType;
	private List<Tag> productTags;
	
	private String createdBy;
	private String lastModifiedBY;
	private String createdDate;
	private String lastModifiedDate;
	
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
	public List<Tag> getProductTags() {
		return productTags;
	}
	public void setProductTags(List<Tag> productTags) {
		this.productTags = productTags;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBY() {
		return lastModifiedBY;
	}
	public void setLastModifiedBY(String lastModifiedBY) {
		this.lastModifiedBY = lastModifiedBY;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
