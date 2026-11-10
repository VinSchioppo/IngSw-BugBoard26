package com.ingsw.bugboard26.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingFieldsException extends RuntimeException
{
    private final String message;

    public MissingFieldsException(final String message)
    {
        super(message);
        this.message = message;
    }
}
