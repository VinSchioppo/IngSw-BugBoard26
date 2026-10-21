package com.ingsw.bugboard26.dto.issuecomment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IssueCommentDto
{
    private String memberUsername;
    private String description;
    private LocalDate date;
}
