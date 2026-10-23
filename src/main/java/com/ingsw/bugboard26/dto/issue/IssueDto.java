package com.ingsw.bugboard26.dto.issue;

import com.ingsw.bugboard26.dto.issuecomment.IssueCommentDto;
import com.ingsw.bugboard26.dto.issueimage.IssueImageDto;
import com.ingsw.bugboard26.dto.issuelog.IssueLogDto;
import com.ingsw.bugboard26.dto.issuetag.IssueTagDto;
import com.ingsw.bugboard26.dto.project.ProjectDto;
import com.ingsw.bugboard26.dto.teammember.TeamMemberInfoDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class IssueDto
{
    private String title;
    private String description;
    private int priority;
    private boolean status;
    private LocalDate dueDate;

    private ProjectDto project;
    private TeamMemberInfoDto teamMember;
    private List<IssueTagDto> tags = new ArrayList<>();
    private List<IssueLogDto> logs = new ArrayList<>();
    private List<IssueCommentDto> comments = new ArrayList<>();
    private List<IssueImageDto> images = new ArrayList<>();
}
