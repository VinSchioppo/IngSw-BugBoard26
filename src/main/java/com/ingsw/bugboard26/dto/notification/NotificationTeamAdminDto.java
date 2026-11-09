package com.ingsw.bugboard26.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTeamAdminDto
{
    private String memberUsername;
    private String description;
}
