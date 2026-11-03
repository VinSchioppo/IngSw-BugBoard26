package com.ingsw.bugboard26.exception.notfound;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMemberNotFoundException extends Exception
{
    private final String message;

    public TeamMemberNotFoundException(final String message)
    {
        super(message);
        this.message = message;
    }
}
