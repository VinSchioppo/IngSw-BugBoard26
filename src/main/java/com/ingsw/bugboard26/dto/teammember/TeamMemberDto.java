package com.ingsw.bugboard26.dto.teammember;

import com.ingsw.bugboard26.dto.issue.IssueInfoDto;
import com.ingsw.bugboard26.dto.issuecomment.IssueCommentMemberDto;
import com.ingsw.bugboard26.dto.notification.NotificationTeamMemberDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamMemberDto
{
    private String email;

    private List<IssueInfoDto> issues = new ArrayList<>();
    private List<NotificationTeamMemberDto> notifications = new ArrayList<>();
    private List<IssueCommentMemberDto> comments = new ArrayList<>();
}
