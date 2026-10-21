package com.ingsw.bugboard26.dto.teammember;

import lombok.Data;

@Data
public class TeamMemberInfoDto
{
    private String username;
    private String email;
    private boolean isAdmin;
}
