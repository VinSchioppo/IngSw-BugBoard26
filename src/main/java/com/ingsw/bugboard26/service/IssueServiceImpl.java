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
    @Autowired(required = false)
    IssueRepository issueRepository;

    @Override
    public Issue selectByIdIssue(int idIssue) {
        return issueRepository.findByIdIssue(idIssue);
    }

    @Override
    public List<Issue> selectByIdProject(int idProject) {
        return issueRepository.findByIdProject(idProject);
    }

    @Override
    public List<Issue> selectByMemberUsername(String username) {
        return issueRepository.findByMemberUsername(username);
    }

    @Override
    public List<Issue> selectByPriority(int prority) {
        return issueRepository.findByPrority(prority);
    }
}
