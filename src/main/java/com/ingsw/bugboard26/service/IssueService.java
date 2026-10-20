package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.Issue;

import java.util.List;

public interface IssueService
{
    Issue selectByIdIssue(int idIssue);
    List<Issue> selectByMemberUsername(String username);
    List<Issue> selectByTag(String nameTag);
    List<Issue> selectByPriority(int prority);
}
