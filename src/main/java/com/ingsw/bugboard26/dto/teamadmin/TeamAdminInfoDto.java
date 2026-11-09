package com.ingsw.bugboard26.dto.teamadmin;

import com.ingsw.bugboard26.dto.teammember.TeamMemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamAdminInfoDto
{
    private TeamMemberInfoDto account;
}
