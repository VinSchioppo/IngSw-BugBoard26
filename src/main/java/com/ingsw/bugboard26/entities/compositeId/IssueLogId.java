package com.ingsw.bugboard26.entities.compositeId;

import com.ingsw.bugboard26.entities.Issue;
import lombok.Data;

@Data
public class IssueLogId
{
    private Issue issue;

    private String description;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
