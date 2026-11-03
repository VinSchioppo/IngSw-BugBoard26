package com.ingsw.bugboard26.exception;

import com.ingsw.bugboard26.exception.duplicate.UsernameDuplicateException;
import com.ingsw.bugboard26.exception.notfound.IssueNotFoundException;
import com.ingsw.bugboard26.exception.notfound.ProjectNotFoundException;
import com.ingsw.bugboard26.exception.notfound.TeamAdminNotFoundException;
import com.ingsw.bugboard26.exception.notfound.TeamMemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler({
            TeamMemberNotFoundException.class,
            TeamAdminNotFoundException.class,
            ProjectNotFoundException.class,
            IssueNotFoundException.class})
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(Exception ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameDuplicateException.class)
    public ResponseEntity<ErrorResponse> duplicateExceptionHandler(Exception ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
