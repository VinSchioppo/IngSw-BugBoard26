package com.ingsw.bugboard26.dto.teammember;

import com.ingsw.bugboard26.dto.issue.IssueInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberIssuesDto
{
    private List<IssueInfoDto> issues = new ArrayList<>();
}
