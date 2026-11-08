package com.ingsw.bugboard26.dto.teammember;

import com.ingsw.bugboard26.dto.notification.NotificationTeamMemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberNotificationsDto
{
    private List<NotificationTeamMemberDto> notifications = new ArrayList<>();
}
