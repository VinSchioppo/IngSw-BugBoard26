package com.ingsw.bugboard26.dto.teammember;

import com.ingsw.bugboard26.dto.issuecomment.IssueCommentMemberDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamMemberCommentsDto
{
    private List<IssueCommentMemberDto> comments = new ArrayList<>();
}
