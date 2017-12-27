package com.codeyasam.posis.dto;

public class ProductTypeDTO {
	
	private long id;
	private String name;
	
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
