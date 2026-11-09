package com.ingsw.bugboard26.dto.teamadmin;

import com.ingsw.bugboard26.dto.notification.NotificationTeamAdminDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamAdminDto
{
    private List<NotificationTeamAdminDto> notifications = new ArrayList<>();
}
