package com.senai.pa3.exceptions.handle;


import com.senai.pa3.exceptions.DataBaseException;
import com.senai.pa3.exceptions.ParametrosException;
import com.senai.pa3.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class StandardHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest http) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(StandardError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .path(http.getRequestURI())
                .build());
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest http) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(StandardError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .path(http.getRequestURI())
                .build());
    }

    @ExceptionHandler(ParametrosException.class)
    public ResponseEntity<StandardError> parametros(ParametrosException e, HttpServletRequest http) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(StandardError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .path(http.getRequestURI())
                .build());
    }
}
