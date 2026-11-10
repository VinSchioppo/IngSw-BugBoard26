package com.ingsw.bugboard26.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(Exception ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> duplicateExceptionHandler(RuntimeException ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MissingFieldsException.class)
    public ResponseEntity<ErrorResponse> missingFieldsExceptionHandler(RuntimeException ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.NO_CONTENT.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }
}
