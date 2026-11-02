package com.ingsw.bugboard26.service.teamadmin;

import com.ingsw.bugboard26.dto.teamadmin.*;

import java.util.List;

public interface TeamAdminService
{
    List<TeamAdminInfoDto> selectAll();
    TeamAdminDto selectByUsername(String username);

    void insertNotifications(String username, TeamAdminDto notifications);

    void updateStatusNotifications(String username, TeamAdminDto notifications);

    boolean deleteAdmin(String username);
    boolean deleteNotifications(String username);
}
