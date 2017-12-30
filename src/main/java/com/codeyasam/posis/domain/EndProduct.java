package com.codeyasam.posis.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name="end_product")
public class EndProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String name;
	
	@ManyToOne
	private ProductType productType;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Tag> productTags;
	
	@CreatedDate
	@Column(updatable=false)
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(insertable=false)
	private LocalDateTime lastModifiedDate;

	@CreatedBy
	@Column(updatable=false)
	private String createdBy;
	
	@LastModifiedBy
	@Column(insertable=false)
	private String lastModifiedBy;		

	public EndProduct() {
		
	}
	
	public EndProduct(long id) {
		this.id = id;
	}
	
	public EndProduct(long id, String name, ProductType productType) {
		this.id = id;
		this.name = name;
		this.productType = productType;
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

	public List<Tag> getProductTags() {
		return productTags;
	}

	public void setProductTags(List<Tag> productTags) {
		this.productTags = productTags;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}	
	
	@Override
	public String toString() {
		return "EndProduct [id=" + id + ", name=" + name + ", productType=" + productType + "]";
	}
	
}
