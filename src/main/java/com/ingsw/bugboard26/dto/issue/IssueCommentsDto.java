package com.ingsw.bugboard26.dto.issue;

import com.ingsw.bugboard26.dto.issuecomment.IssueCommentDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IssueCommentsDto
{
    private List<IssueCommentDto> comments = new ArrayList<>();
}
