package com.codingMart.oms.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Id Not Found")  
public class IdNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public IdNotFoundException(String id)   
	{  
		super("Id: "+id+" not found"); 
	}  
}
