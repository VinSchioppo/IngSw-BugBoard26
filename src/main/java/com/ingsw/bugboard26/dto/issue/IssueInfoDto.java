package com.ingsw.bugboard26.dto.issue;

import com.ingsw.bugboard26.dto.issueimage.IssueImageDto;
import com.ingsw.bugboard26.dto.issuetag.IssueTagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueInfoDto
{
    private int idIssue;
    private String memberUsername;
    private String title;
    private String description;
    private int priority;
    private Boolean status;
    private LocalDate dueDate;

    private List<IssueTagDto> tags = new ArrayList<>();
    private List<IssueImageDto> images = new ArrayList<>();
}
