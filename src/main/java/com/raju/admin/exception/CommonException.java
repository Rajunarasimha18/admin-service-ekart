package com.raju.admin.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {
	   private static final long serialVersionUID = -737854953067276230L;
	   private final String message;
	   private final HttpStatus httpStatus;

	   public CommonException(String message, HttpStatus httpStatus) {
	      this.message = message;
	      this.httpStatus = httpStatus;
	   }

	   @Override
	public String getMessage() {
	      return this.message;
	   }

	   public HttpStatus getHttpStatus() {
	      return this.httpStatus;
	   }
	}

