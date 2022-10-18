package com.codingMart.oms.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class StatusException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public StatusException(String err)   
	{  
		super(err); 
	}  
}
