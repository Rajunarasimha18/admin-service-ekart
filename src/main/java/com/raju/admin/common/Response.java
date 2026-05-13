package com.raju.admin.common;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Response<T> {
	private String status;
	private HttpStatus httpStatus;
	private String message;
	private T data;

	public static<T> Response<T> returnResponse(T data, String message, HttpStatus httpStatus, String status) {
		Response<T> res = new Response<>();
		res.setData(data);
		res.setMessage(message);
		res.setHttpStatus(httpStatus);
		res.setStatus(status);
		return res;
	}

	public static<T> Response<T> returnResponse(String message, HttpStatus httpStatus, String status) {
		Response<T> res = new Response();
		res.setMessage(message);
		res.setHttpStatus(httpStatus);
		res.setStatus(status);
		return res;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
