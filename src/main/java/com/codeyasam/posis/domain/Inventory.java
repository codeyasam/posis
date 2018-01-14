package com.codeyasam.posis.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="inventory")
public class Inventory extends Auditable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private EndProduct product;
	
	@Min(value=0)
	private int stockQuantity;
	
	private double acquiredPrice;
	private double sellingPrice;

	public Inventory() {
		
	}
	
	public Inventory(long id, EndProduct product, int stockQuantity, double acquiredPrice, double sellingPrice) {
		this.id = id;
		this.product = product;
		this.stockQuantity = stockQuantity;
		this.acquiredPrice = acquiredPrice;
		this.sellingPrice = sellingPrice;
	}
	
	public Inventory(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public double getAcquiredPrice() {
		return acquiredPrice;
	}

	public void setAcquiredPrice(double acquiredPrice) {
		this.acquiredPrice = acquiredPrice;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}	
	
	public EndProduct getProduct() {
		return product;
	}

	public void setProduct(EndProduct product) {
		this.product = product;
	}	
}
