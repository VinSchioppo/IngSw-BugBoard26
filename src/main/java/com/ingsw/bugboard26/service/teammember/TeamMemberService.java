package com.ingsw.bugboard26.service.teammember;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.exception.duplicate.UsernameDuplicateException;

import java.util.List;

public interface TeamMemberService
{
    List<TeamMemberInfoDto> selectAll();
    TeamMemberDto selectByUsername(String username);
    TeamMemberIssuesDto selectIssuesByUsername(String username);
    TeamMemberCommentsDto selectCommentsByUsername(String username);
    TeamMemberNotificationsDto selectNotificationsByUsername(String username);

    void insertTeamMembers(List<TeamMemberInfoDto> teamMembers) throws UsernameDuplicateException;
    void insertComments(String username, TeamMemberCommentsDto comments);
    void insertNotifications(String username, TeamMemberNotificationsDto notifications);

    void updatePassword(String username, TeamMemberInfoDto teamMemberDto);
    void updateToAdmin(List<TeamMemberInfoDto> teamMembers);
    void updateStatusNotifications(String username, TeamMemberNotificationsDto notifications);

    boolean deleteMember(String username);
    boolean deleteNotifications(String username);
}
