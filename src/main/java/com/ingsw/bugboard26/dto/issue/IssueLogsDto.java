package com.ingsw.bugboard26.dto.issue;

import com.ingsw.bugboard26.dto.issuelog.IssueLogDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IssueLogsDto
{
    private List<IssueLogDto> logs = new ArrayList<>();
}
