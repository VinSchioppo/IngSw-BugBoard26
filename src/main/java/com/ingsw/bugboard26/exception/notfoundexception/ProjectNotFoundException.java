package com.ingsw.bugboard26.exception.notfoundexception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectNotFoundException extends Exception
{
    private final String message;

    public ProjectNotFoundException(final String message)
    {
        super(message);
        this.message = message;
    }
}
