package com.ingsw.bugboard26.service.teammember;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.NotFoundException;

import java.util.List;

public interface TeamMemberService
{
    List<TeamMemberInfoDto> selectAll();
    TeamMemberDto selectByUsername(String username);
    TeamMemberIssuesDto selectIssuesByUsername(String username);
    TeamMemberCommentsDto selectCommentsByUsername(String username);
    TeamMemberNotificationsDto selectNotificationsByUsername(String username);

    void insertTeamMembers(List<TeamMemberInfoDto> teamMembers) throws DuplicateException;
    void insertComments(String username, TeamMemberCommentsDto comments) throws NotFoundException, DuplicateException;
    void insertNotifications(String username, TeamMemberNotificationsDto notifications) throws NotFoundException, DuplicateException;

    void updatePassword(String username, TeamMemberInfoDto teamMemberDto);
    void updateToAdmin(List<TeamMemberInfoDto> teamMembers) throws NotFoundException;
    void updateStatusNotifications(String username, TeamMemberNotificationsDto notifications);

    void deleteMember(String username) throws NotFoundException;
    void deleteNotifications(String username) throws NotFoundException;
}
