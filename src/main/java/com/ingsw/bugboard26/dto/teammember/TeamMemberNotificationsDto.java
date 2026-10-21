package com.ingsw.bugboard26.dto.teammember;

import com.ingsw.bugboard26.dto.notification.NotificationTeamMemberDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamMemberNotificationsDto
{
    private List<NotificationTeamMemberDto> notifications = new ArrayList<>();
}
