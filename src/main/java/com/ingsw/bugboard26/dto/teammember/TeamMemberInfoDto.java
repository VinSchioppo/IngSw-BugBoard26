package com.ingsw.bugboard26.dto.teammember;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberInfoDto
{
    private String username;
    private String email;
    private String password;
    private Boolean isAdmin;
}
