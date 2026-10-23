package com.ingsw.bugboard26.dto.issue;

import com.ingsw.bugboard26.dto.teammember.TeamMemberInfoDto;
import lombok.Data;

@Data
public class IssueMemberDto
{
    private TeamMemberInfoDto teamMember;
}
