package com.ingsw.bugboard26.dto.issue;

import com.ingsw.bugboard26.dto.issuecomment.IssueCommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueCommentsDto
{
    private List<IssueCommentDto> comments = new ArrayList<>();
}
