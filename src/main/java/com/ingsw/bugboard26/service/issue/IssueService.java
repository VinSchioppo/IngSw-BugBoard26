package com.ingsw.bugboard26.service.issue;

import com.ingsw.bugboard26.dto.issue.*;

import java.util.List;

public interface IssueService
{
    IssueDto selectByIdIssue(int idIssue);
    IssueCommentsDto selectCommentsByIdIssue(int idIssue);
    IssueLogsDto selectLogsByIdIssue(int idIssue);
    List<IssueInfoDto> selectByMemberUsername(String username);
    List<IssueInfoDto> selectByTag(String nameTag);
    List<IssueInfoDto> selectByPriority(int priority);

    void insertMember(int idIssue, IssueMemberDto teamMember);
    void insertLogs(int idIssue, IssueLogsDto logs);
}
