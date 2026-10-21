package com.ingsw.bugboard26.dto.issue;

import com.ingsw.bugboard26.dto.issueimage.IssueImageDto;
import com.ingsw.bugboard26.dto.issuetag.IssueTagDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class IssueInfoDto
{
    private int idIssue;
    private String title;
    private String description;
    private int priority;
    private boolean status;
    private LocalDate dueDate;

    private List<IssueTagDto> tags = new ArrayList<>();
    private List<IssueImageDto> images = new ArrayList<>();
}
