package com.ingsw.bugboard26.dto.issuecomment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueCommentDto
{
    private String memberUsername;
    private String description;
    private LocalDate date;
}
