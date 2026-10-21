package com.ingsw.bugboard26.dto.teammember;

import com.ingsw.bugboard26.dto.issue.IssueInfoDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamMemberIssuesDto
{
    private List<IssueInfoDto> issues = new ArrayList<>();
}
