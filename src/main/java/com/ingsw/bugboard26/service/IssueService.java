package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.Issue;

import java.util.List;

public interface IssueService
{
    Issue selectByIdIssue(int idIssue);
    List<Issue> selectByIdProject(int idProject);
    List<Issue> selectByMemberUsername(String username);
    List<Issue> selectByPriority(int prority);
}
