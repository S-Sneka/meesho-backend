package com.codingMart.oms.common;

import org.springframework.stereotype.Component;

import org.springframework.http.HttpStatus;
@Component
public class APIResponse {
	private Integer status;
	private Object data;
	private Object error;
	
	
	public APIResponse() {
		super();
		this.status = HttpStatus.OK.value();
	}
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getError() {
		return error;
	}
	public void setError(Object error) {
		this.error = error;
	}
	
}
