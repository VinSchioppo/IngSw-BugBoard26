package com.ingsw.bugboard26.exception.notfound;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueNotFoundException extends Exception
{
    private final String message;

    public IssueNotFoundException(final String message)
    {
        super(message);
        this.message = message;
    }
}
