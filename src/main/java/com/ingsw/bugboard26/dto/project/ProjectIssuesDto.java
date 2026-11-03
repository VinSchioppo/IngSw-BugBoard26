package com.ingsw.bugboard26.dto.project;

import com.ingsw.bugboard26.dto.issue.IssueDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectIssuesDto
{
    private String title;

    private List<IssueDto> issues = new ArrayList<>();
}
