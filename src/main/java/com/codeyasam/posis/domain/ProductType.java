package com.codeyasam.posis.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.codeyasam.posis.projection.ProductTypeProjection;

@Entity
@Table(name="product_type")
@SqlResultSetMapping(name = "ProductTypeMapping", classes = {
		@ConstructorResult(targetClass = ProductTypeProjection.class, 
				columns = {
						@ColumnResult(name="type_id", type=Long.class),
						@ColumnResult(name="type_name", type=String.class)
				})
})
@EntityListeners(AuditingEntityListener.class)
public class ProductType {
	
	@Id
//	@Column(columnDefinition="serial")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String name;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
