package com.ingsw.bugboard26.entities.compositeId;

import lombok.Data;

import java.io.Serializable;

@Data
public class IssueCommentId implements Serializable
{
    private String memberUsername;

    private int idIssue;

    private String description;

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
