package com.ingsw.bugboard26.dto.teamadmin;

import com.ingsw.bugboard26.dto.notification.NotificationTeamAdminDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamAdminDto
{
    private List<NotificationTeamAdminDto> notifications = new ArrayList<>();
}
