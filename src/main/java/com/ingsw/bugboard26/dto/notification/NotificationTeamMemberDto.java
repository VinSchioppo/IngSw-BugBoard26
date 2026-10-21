package com.ingsw.bugboard26.dto.notification;

import lombok.Data;

@Data
public class NotificationTeamMemberDto
{
    private String adminUsername;
    private String description;
    private Boolean status;
}
