package com.ingsw.bugboard26.service.teamadmin;

import com.ingsw.bugboard26.dto.teamadmin.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.NotFoundException;

import java.util.List;

public interface TeamAdminService
{
    List<TeamAdminInfoDto> selectAll();
    TeamAdminDto selectByUsername(String username);

    void insertNotifications(String username, TeamAdminDto notifications) throws DuplicateException;

    void updateStatusNotifications(String username, TeamAdminDto notifications);

    void deleteAdmin(String username) throws NotFoundException;
    void deleteNotifications(String username) throws NotFoundException;
}
