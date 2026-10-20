package com.ingsw.bugboard26.repository;

import com.ingsw.bugboard26.entities.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface TeamMemberRepository extends JpaRepository<TeamMember, String>
{
    TeamMember findByUsername(String username);

    @Query("select new TeamMember(tm.username, tm.email) from TeamMember tm")
    List<TeamMember> findAllTeamMemberInfo();
}
