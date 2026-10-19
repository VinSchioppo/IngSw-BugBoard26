package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.TeamMember;

import java.util.List;

public interface TeamMemberService
{
    List<TeamMember> selectAll();
    TeamMember selectByUserame(String username);
}
