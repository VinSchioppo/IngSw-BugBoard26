package com.ingsw.bugboard26.service.issue;

import com.ingsw.bugboard26.dto.issue.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;

import java.util.List;

public interface IssueService
{
    IssueDto selectByIdIssue(int idIssue);
    IssueCommentsDto selectCommentsByIdIssue(int idIssue);
    IssueLogsDto selectLogsByIdIssue(int idIssue);
    List<IssueInfoDto> selectByMemberUsername(String username);
    List<IssueInfoDto> selectByTag(String nameTag);
    List<IssueInfoDto> selectByPriority(int priority);

    void insertMember(int idIssue, IssueMemberDto teamMember) throws MissingFieldsException, NotFoundException;
    void insertLogs(int idIssue, IssueLogsDto logs) throws MissingFieldsException, NotFoundException, DuplicateException;

    void updateStatus(int idIssue, IssueInfoDto issueInfoDto) throws MissingFieldsException, NotFoundException;

    void deleteIssue(int idIssue) throws NotFoundException;

    String adviceMember();
}
