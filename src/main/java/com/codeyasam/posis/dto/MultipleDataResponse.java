package com.codeyasam.posis.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public class MultipleDataResponse<T> {
	
	private long total;
	private List<T> data;
	private String prompt;
	private HttpStatus status;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}
