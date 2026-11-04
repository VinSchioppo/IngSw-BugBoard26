package com.ingsw.bugboard26.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends Exception
{
    private final String message;

    public NotFoundException(final String message)
    {
        super(message);
        this.message = message;
    }
}
