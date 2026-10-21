package com.ingsw.bugboard26.dto.notification;

import lombok.Data;

@Data
public class NotificationTeamAdminDto
{
    private String memberUsername;
    private String description;
    private Boolean status;
}
