package com.ecommerce.Ecommerce.exception;

import com.ecommerce.Ecommerce.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleProductNotFoundException(ProductNotFoundException ex){
        ApiResponse<String> response = new ApiResponse<String>("fail",ex.getMessage());
        return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.NOT_FOUND);
    }
}
