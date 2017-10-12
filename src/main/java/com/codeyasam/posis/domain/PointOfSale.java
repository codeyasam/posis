package com.codeyasam.posis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="point_of_sale")
public class PointOfSale {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private Inventory inventory;
	
	private int productQuantity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	
}
