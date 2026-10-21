package com.ingsw.bugboard26.service.converter;

import com.ingsw.bugboard26.dto.issue.*;
import com.ingsw.bugboard26.entities.Issue;
import org.modelmapper.ModelMapper;

import java.util.List;

public class IssueConverter
{
    private IssueConverter() {}

    public static IssueDto convertToDto(Issue issue, ModelMapper modelMapper)
    {
        IssueDto issueDto = null;

        if (issue != null)
        {
            issueDto =  modelMapper.map(issue, IssueDto.class);
        }

        return issueDto;
    }

    public static List<IssueDto> convertToDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(source -> convertToDto(source, modelMapper))
                .toList();
    }

    public static IssueInfoDto convertToInfoDto(Issue issue, ModelMapper modelMapper)
    {
        IssueInfoDto issueDto = null;

        if (issue != null)
        {
            issueDto =  modelMapper.map(issue, IssueInfoDto.class);
        }

        return issueDto;
    }

    public static List<IssueInfoDto> convertToInfoDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(source -> convertToInfoDto(source, modelMapper))
                .toList();
    }

    public static IssueLogsDto convertToLogsDto(Issue issue, ModelMapper modelMapper)
    {
        IssueLogsDto issueDto = null;

        if (issue != null)
        {
            issueDto =  modelMapper.map(issue, IssueLogsDto.class);
        }

        return issueDto;
    }

    public static List<IssueLogsDto> convertToLogsDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(source -> convertToLogsDto(source, modelMapper))
                .toList();
    }

    public static IssueCommentsDto convertToCommentsDto(Issue issue, ModelMapper modelMapper)
    {
        IssueCommentsDto issueDto = null;

        if (issue != null)
        {
            issueDto =  modelMapper.map(issue, IssueCommentsDto.class);
        }

        return issueDto;
    }

    public static List<IssueCommentsDto> convertToCommentsDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(source -> convertToCommentsDto(source, modelMapper))
                .toList();
    }
}
