package com.ingsw.bugboard26.repository;

import com.ingsw.bugboard26.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface IssueRepository extends JpaRepository<Issue, Integer>
{
    Issue findByIdIssue(int idIssue);
    List<Issue> findByTeamMemberUsername(String username);
    List<Issue> findByTagsNameTag(String nameTag);
    List<Issue> findByPriority(int prority);
}
