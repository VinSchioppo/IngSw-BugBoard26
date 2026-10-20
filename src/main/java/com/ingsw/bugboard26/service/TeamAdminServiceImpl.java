package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.TeamAdmin;
import com.ingsw.bugboard26.repository.TeamAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamAdminServiceImpl implements TeamAdminService
{
    private final TeamAdminRepository teamAdminRepository;

    @Autowired
    public TeamAdminServiceImpl(final TeamAdminRepository teamAdminRepository)
    {
        this.teamAdminRepository = teamAdminRepository;
    }

    @Override
    public TeamAdmin selectByUserame(String username)
    {
        return teamAdminRepository.findByAccountUsername(username);
    }
}
