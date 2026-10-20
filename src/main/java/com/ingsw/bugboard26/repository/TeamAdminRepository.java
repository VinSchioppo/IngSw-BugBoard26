package com.ingsw.bugboard26.repository;

import com.ingsw.bugboard26.entities.TeamAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TeamAdminRepository extends JpaRepository<TeamAdmin, String>
{
    TeamAdmin findByAccountUsername(String username);
}
