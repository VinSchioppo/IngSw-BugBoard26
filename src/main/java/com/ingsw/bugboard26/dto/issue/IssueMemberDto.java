package com.ingsw.bugboard26.dto.issue;

import com.ingsw.bugboard26.dto.teammember.TeamMemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueMemberDto
{
    private TeamMemberInfoDto teamMember;
}
