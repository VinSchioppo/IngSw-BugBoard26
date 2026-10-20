package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.Issue;
import com.ingsw.bugboard26.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IssueServiceImpl implements IssueService
{
    private final IssueRepository issueRepository;

    @Autowired
    public IssueServiceImpl(final IssueRepository issueRepository)
    {
        this.issueRepository = issueRepository;
    }

    @Override
    public Issue selectByIdIssue(int idIssue)
    {
        return issueRepository.findByIdIssue(idIssue);
    }

    @Override
    public List<Issue> selectByMemberUsername(String username)
    {
        return issueRepository.findByTeamMemberUsername(username);
    }

    @Override
    public List<Issue> selectByTag(String nameTag)
    {
        return issueRepository.findByTagsNameTag(nameTag);
    }

    @Override
    public List<Issue> selectByPriority(int priority)
    {
        return issueRepository.findByPriority(priority);
    }
}
