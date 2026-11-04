package com.ingsw.bugboard26.dto.project;

import com.ingsw.bugboard26.dto.issue.IssueInfoDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectIssuesDto
{
    private String title;

    private List<IssueInfoDto> issues = new ArrayList<>();
}
