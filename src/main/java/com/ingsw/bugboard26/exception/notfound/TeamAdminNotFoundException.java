package com.ingsw.bugboard26.exception.notfound;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamAdminNotFoundException extends Exception
{
    private final String message;

    public TeamAdminNotFoundException(final String message)
    {
        super(message);
        this.message = message;
    }
}
