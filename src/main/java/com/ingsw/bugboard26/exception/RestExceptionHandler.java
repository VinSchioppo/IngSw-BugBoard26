package com.ingsw.bugboard26.exception;

import com.ingsw.bugboard26.exception.notfoundexception.IssueNotFoundException;
import com.ingsw.bugboard26.exception.notfoundexception.ProjectNotFoundException;
import com.ingsw.bugboard26.exception.notfoundexception.TeamAdminNotFoundException;
import com.ingsw.bugboard26.exception.notfoundexception.TeamMemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(TeamMemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> teamMemberNotFoundHandler(Exception ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeamAdminNotFoundException.class)
    public ResponseEntity<ErrorResponse> teamAdminNotFoundHandler(Exception ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> projectNotFoundHandler(Exception ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<ErrorResponse> issueNotFoundHandler(Exception ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
