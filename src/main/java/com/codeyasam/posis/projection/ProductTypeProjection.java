package com.codeyasam.posis.projection;

public class ProductTypeProjection {
	
	private long type_id;
	private String type_name;

	public ProductTypeProjection(long type_id, String type_name) {
		this.type_id = type_id;
		this.type_name = type_name;
	}
	
	public long getTypeId() {
		return type_id;
	}

	public void setTypeId(long typeId) {
		this.type_id = typeId;
	}

	public String getTypeName() {
		return type_name;
	}

	public void setTypeName(String typeName) {
		this.type_name = typeName;
	}
}
