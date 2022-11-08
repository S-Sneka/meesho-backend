package com.codingMart.oms.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codingMart.oms.exception.IdNotFoundException;
import com.codingMart.oms.exception.StatusException;


@ControllerAdvice
public class ExceptionHandling {
	
	APIResponse apiResponse;
	public ExceptionHandling(APIResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<APIResponse> handleBadRequestException(IdNotFoundException e){
    	apiResponse.setData(null);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setError(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }
    
    @ExceptionHandler(StatusException.class)
    public ResponseEntity<APIResponse> handleBadRequestException(StatusException e){
    	apiResponse.setData(null);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setError(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

//    @ExceptionHandler()
//    public ResponseEntity<APIResponse> handleException(Exception e){
//    	apiResponse.setData(null);
//    	apiResponse.setError("Unknown error Occured");
//    	apiResponse.setStatus(HttpStatus.OK.value());
//    	
//    	return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
//    }
}