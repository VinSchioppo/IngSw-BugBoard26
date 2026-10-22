package com.ingsw.bugboard26.service.teamadmin;

import com.ingsw.bugboard26.dto.teamadmin.*;

public interface TeamAdminService
{
    TeamAdminDto selectByUserame(String username);

    void insertNotifications(String username, TeamAdminDto notifications);
}
