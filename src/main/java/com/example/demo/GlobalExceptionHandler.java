package com.example.demo;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IOException.class)
    public void handleIOException(IOException ex) {
        // Log the exception or handle it as per your application's requirements
        // For example:
        System.err.println("IOException occurred: " + ex.getMessage());
    }
}
