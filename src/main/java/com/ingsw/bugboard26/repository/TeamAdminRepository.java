package com.ingsw.bugboard26.repository;

import com.ingsw.bugboard26.entities.TeamAdmin;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TeamAdminRepository
{
    TeamAdmin findByUsername(String username);
}
