package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.issue.*;
import com.ingsw.bugboard26.entity.Issue;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class IssueConverter
{
    private IssueConverter() {}

    public static IssueDto convertToDto(Issue issue, ModelMapper modelMapper)
    {
        IssueDto issueDto = null;

        if (issue != null)
            issueDto =  modelMapper.map(issue, IssueDto.class);

        return issueDto;
    }

    public static List<IssueDto> convertToDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(issue -> convertToDto(issue, modelMapper))
                .toList();
    }

    public static IssueInfoDto convertToInfoDto(Issue issue, ModelMapper modelMapper)
    {
        IssueInfoDto issueDto = null;

        if (issue != null)
            issueDto =  modelMapper.map(issue, IssueInfoDto.class);

        return issueDto;
    }

    public static List<IssueInfoDto> convertToInfoDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(issue -> convertToInfoDto(issue, modelMapper))
                .toList();
    }

    public static IssueMemberDto convertToMemberDto(Issue issue, ModelMapper modelMapper)
    {
        IssueMemberDto issueDto = null;

        if (issue != null)
            issueDto =  modelMapper.map(issue, IssueMemberDto.class);

        return issueDto;
    }

    public static IssueLogsDto convertToLogsDto(Issue issue, ModelMapper modelMapper)
    {
        IssueLogsDto issueDto = null;

        if (issue != null)
            issueDto =  modelMapper.map(issue, IssueLogsDto.class);

        return issueDto;
    }

    public static List<IssueLogsDto> convertToLogsDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(issue -> convertToLogsDto(issue, modelMapper))
                .toList();
    }

    public static IssueCommentsDto convertToCommentsDto(Issue issue, ModelMapper modelMapper)
    {
        IssueCommentsDto issueDto = null;

        if (issue != null)
            issueDto =  modelMapper.map(issue, IssueCommentsDto.class);

        return issueDto;
    }

    public static List<IssueCommentsDto> convertToCommentsDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(issue -> convertToCommentsDto(issue, modelMapper))
                .toList();
    }

    public static Issue convertFromMemberDto(Issue issue, IssueMemberDto issueDto, ModelMapper modelMapper)
    {
        if(issue != null && issueDto != null)
        {
            Issue tmpIssue = modelMapper.map(issueDto, Issue.class);
            tmpIssue.getTeamMember().addIssue(issue);
            issue.setTeamMember(tmpIssue.getTeamMember());
        }

        return issue;
    }

    public static Issue convertFromLogsDto(Issue issue, IssueLogsDto issueDto, ModelMapper modelMapper)
    {
        if(issue != null && issueDto != null)
        {
            Issue tmpIssue = modelMapper.map(issueDto, Issue.class);
            tmpIssue.getIssueLogs().forEach(
                    issueLog -> {
                        issueLog.setIssue(issue);
                        issueLog.setDate(LocalDate.now(ZoneId.systemDefault()));
                        issue.addLog(issueLog);
                    });
        }

        return issue;
    }
}
