package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.TeamMember;
import com.ingsw.bugboard26.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamMemberServiceImpl implements TeamMemberService
{
    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Override
    public List<TeamMember> selectAll()
    {
        return teamMemberRepository.findAll();
    }

    @Override
    public TeamMember selectByUserame(String username)
    {
        return teamMemberRepository.findByUsername(username);
    }
}
