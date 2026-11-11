package com.ingsw.bugboard26.service.issue;

import com.ingsw.bugboard26.dto.issue.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;

import java.util.List;

public interface IssueService
{
    IssueDto selectByIdIssue(Long idIssue) throws NotFoundException;
    IssueCommentsDto selectCommentsByIdIssue(Long idIssue) throws NotFoundException;
    IssueLogsDto selectLogsByIdIssue(Long idIssue) throws NotFoundException;
    List<IssueInfoDto> selectByMemberUsername(String username);
    List<IssueInfoDto> selectByTag(String nameTag);
    List<IssueInfoDto> selectByPriority(int priority);

    void insertLogs(Long idIssue, IssueLogsDto logs) throws MissingFieldsException, NotFoundException, DuplicateException;
    void insertMember(Long idIssue, IssueMemberDto teamMember) throws MissingFieldsException, NotFoundException;

    void updateStatus(Long idIssue, IssueInfoDto issueInfoDto) throws MissingFieldsException, NotFoundException;

    void deleteIssue(Long idIssue) throws NotFoundException;

    String adviceMember();
}
