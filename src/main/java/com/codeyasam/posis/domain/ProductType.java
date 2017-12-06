package com.codeyasam.posis.domain;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

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
public class ProductType {
	
	@Id
//	@Column(columnDefinition="serial")	
	@GeneratedValue(strategy=GenerationType.AUTO)
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
