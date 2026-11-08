package com.ingsw.bugboard26.entity.compositeId;

import lombok.Data;

@Data
public class IssueCommentId
{
    private int idIssue;

    private String memberUsername;

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
