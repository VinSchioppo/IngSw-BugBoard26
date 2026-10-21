package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.dto.teamadmin.*;

public interface TeamAdminService
{
    TeamAdminDto selectByUserame(String username);
}
