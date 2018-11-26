package com.services.direct.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.errors.ErrorDto;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorMessage {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private List<ErrorDto> errors = new ArrayList<>();

	public ErrorMessage() {
	}

	public ErrorMessage(String message, List<ErrorDto> errors) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.errors = errors;
	}
	
	public ErrorMessage(LocalDateTime timestamp, String message, List<ErrorDto> errors) {
		this.timestamp = timestamp;
		this.message = message;
		this.errors = errors;
	}

	public ErrorMessage(String message) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ErrorDto> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDto> errors) {
		this.errors = errors;
	}
	
	public ErrorMessage add(ErrorDto err) {
	    errors.add(err);
	    return this;
	  }
}
