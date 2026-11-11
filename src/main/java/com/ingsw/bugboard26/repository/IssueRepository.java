package com.ingsw.bugboard26.repository;

import com.ingsw.bugboard26.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface IssueRepository extends JpaRepository<Issue, Integer>
{
    Issue findByIdIssue(Long idIssue);
    List<Issue> findByTeamMemberUsername(String username);
    List<Issue> findByTagsNameTag(String nameTag);
    List<Issue> findByPriority(int prority);

    @Query("select i.teamMember.username, count(i.teamMember) as countMember " +
            "from Issue as i " +
            "where i.teamMember is not null " +
            "group by i.teamMember " +
            "order by countMember")
    List<Object[]> findIssuesAssigned();
}
