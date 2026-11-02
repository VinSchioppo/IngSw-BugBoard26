package com.ingsw.bugboard26.exception;

import lombok.Data;

@Data
public class ErrorResponse
{
    private int code;
    private String message;
}
