package com.codeyasam.posis.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codeyasam.posis.exception.PageNotFoundException;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(PageNotFoundException.class)
	public void handlePageNotFoundException(PageNotFoundException exception, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
}
