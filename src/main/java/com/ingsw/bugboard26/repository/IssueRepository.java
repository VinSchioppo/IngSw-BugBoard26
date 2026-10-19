package com.ingsw.bugboard26.repository;

import com.ingsw.bugboard26.entities.Issue;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface IssueRepository
{
    Issue findByIdIssue(int idIssue);
    List<Issue> findByIdProject(int idProject);
    List<Issue> findByMemberUsername(String username);
    List<Issue> findByPrority(int prority);
}
