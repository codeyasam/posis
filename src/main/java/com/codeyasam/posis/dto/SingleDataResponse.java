package com.codeyasam.posis.dto;

public class SingleDataResponse<T> {
	
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
