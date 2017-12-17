package com.codeyasam.posis.dto;

public abstract class DataResponse {
	
	private String prompt;
	private int status;
	
	public final String getPrompt() {
		return prompt;
	}
	
	public final void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public final int getStatus() {
		return status;
	}
	
	public final void setStatus(int status) {
		this.status = status;
	}
}
