package com.ingsw.bugboard26.dto.teammember;

import com.ingsw.bugboard26.dto.issuecomment.IssueCommentMemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberCommentsDto
{
    private List<IssueCommentMemberDto> comments = new ArrayList<>();
}
