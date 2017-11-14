package com.codeyasam.posis.restcontroller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codeyasam.posis.exception.PageNotFoundException;
import com.codeyasam.posis.exception.UserAlreadyExistException;
import com.codeyasam.posis.exception.UserNotFoundException;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(PageNotFoundException.class)
	public void handlePageNotFoundException(PageNotFoundException exception, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public void handleUserAlreadyExistException(UserAlreadyExistException exception, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), exception.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public void handleUserNotFoundException(UserNotFoundException exception, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
}
