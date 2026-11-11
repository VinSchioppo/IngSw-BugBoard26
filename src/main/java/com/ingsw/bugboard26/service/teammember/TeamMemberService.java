package com.ingsw.bugboard26.service.teammember;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;

import java.util.List;

public interface TeamMemberService
{
    List<TeamMemberInfoDto> selectAll();
    TeamMemberDto selectByUsername(String username) throws NotFoundException;
    TeamMemberIssuesDto selectIssuesByUsername(String username) throws NotFoundException;
    TeamMemberCommentsDto selectCommentsByUsername(String username) throws NotFoundException;
    TeamMemberNotificationsDto selectNotificationsByUsername(String username) throws NotFoundException;

    void insertTeamMembers(List<TeamMemberInfoDto> teamMembers) throws MissingFieldsException, DuplicateException;
    void insertComments(String username, TeamMemberCommentsDto comments) throws MissingFieldsException, NotFoundException, DuplicateException;
    void insertNotifications(String username, TeamMemberNotificationsDto notifications) throws MissingFieldsException, NotFoundException, DuplicateException;

    void updatePassword(String username, TeamMemberInfoDto teamMemberDto) throws MissingFieldsException, NotFoundException;
    void updateToAdmin(List<TeamMemberInfoDto> teamMembers) throws MissingFieldsException, NotFoundException;
    void updateStatusNotifications(String username, TeamMemberNotificationsDto notifications) throws MissingFieldsException, NotFoundException;

    void deleteMember(String username) throws NotFoundException;
    void deleteNotifications(String username) throws NotFoundException;
}
