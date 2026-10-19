package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.TeamAdmin;

public interface TeamAdminService
{
    TeamAdmin selectByUserame(String username);
}
