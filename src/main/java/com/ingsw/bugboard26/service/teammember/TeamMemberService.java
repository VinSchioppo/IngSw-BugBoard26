package com.ingsw.bugboard26.service.teammember;

import com.ingsw.bugboard26.dto.teammember.*;

import java.util.List;

public interface TeamMemberService
{
    List<TeamMemberInfoDto> selectAll();
    TeamMemberDto selectByUsername(String username);
    TeamMemberIssuesDto selectIssuesByUsername(String username);
    TeamMemberCommentsDto selectCommentsByUsername(String username);
    TeamMemberNotificationsDto selectNotificationsByUsername(String username);

    void insertNotifications(String username, TeamMemberNotificationsDto notifications);
}
