package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.issue.*;
import com.ingsw.bugboard26.entity.Issue;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class IssueConverter
{
    private IssueConverter() {}

    public static IssueDto convertToDto(Issue issue, ModelMapper modelMapper)
            throws NotFoundException
    {
        if (issue == null)
            throw new NotFoundException("Issue non trovata.");

        return modelMapper.map(issue, IssueDto.class);
    }

    public static List<IssueDto> convertToDto(List<Issue> issues, ModelMapper modelMapper)
            throws NotFoundException
    {
        return issues
                .stream()
                .map(issue -> convertToDto(issue, modelMapper))
                .toList();
    }

    public static IssueMemberDto convertToMemberDto(Issue issue, ModelMapper modelMapper)
    {
        if (issue != null)
            return modelMapper.map(issue, IssueMemberDto.class);

        return null;
    }

    public static IssueLogsDto convertToLogsDto(Issue issue, ModelMapper modelMapper)
            throws NotFoundException
    {
        if (issue == null)
            throw new NotFoundException("Issue non trovata.");

        return modelMapper.map(issue, IssueLogsDto.class);
    }

    public static List<IssueLogsDto> convertToLogsDto(List<Issue> issues, ModelMapper modelMapper)
            throws NotFoundException
    {
        return issues
                .stream()
                .map(issue -> convertToLogsDto(issue, modelMapper))
                .toList();
    }

    public static IssueCommentsDto convertToCommentsDto(Issue issue, ModelMapper modelMapper)
            throws NotFoundException
    {
        if (issue == null)
            throw new NotFoundException("Issue non trovata.");

        return modelMapper.map(issue, IssueCommentsDto.class);
    }

    public static List<IssueCommentsDto> convertToCommentsDto(List<Issue> issues, ModelMapper modelMapper)
            throws NotFoundException
    {
        return issues
                .stream()
                .map(issue -> convertToCommentsDto(issue, modelMapper))
                .toList();
    }

    public static IssueInfoDto convertToInfoDto(Issue issue, ModelMapper modelMapper)
    {
        if (issue != null)
            return modelMapper.map(issue, IssueInfoDto.class);

        return null;
    }

    public static List<IssueInfoDto> convertToInfoDto(List<Issue> issues, ModelMapper modelMapper)
    {
        return issues
                .stream()
                .map(issue -> convertToInfoDto(issue, modelMapper))
                .toList();
    }

    public static Issue convertFromMemberDto(Issue issue, IssueMemberDto issueDto, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(issue == null)
            throw new NotFoundException("Issue non trovata.");

        Issue tmpIssue = modelMapper.map(issueDto, Issue.class);
        tmpIssue.getTeamMember().getIssues().add(issue);
        issue.setTeamMember(tmpIssue.getTeamMember());

        return issue;
    }

    public static Issue convertFromLogsDto(Issue issue, IssueLogsDto issueDto, ModelMapper modelMapper)
            throws MissingFieldsException, NotFoundException, DuplicateException
    {
        if(issue == null)
            throw new NotFoundException("Issue non trovata.");

        Issue tmpIssue = modelMapper.map(issueDto, Issue.class);
        tmpIssue.getIssueLogs().forEach(
                issueLog -> {
                    if(issueLog.getDescription() == null || issueLog.getDescription().isEmpty())
                        throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

                    issueLog.setIssue(issue);
                    issueLog.setDate(LocalDate.now(ZoneId.systemDefault()));

                    if(issue.getIssueLogs().contains(issueLog))
                        throw new DuplicateException("Questo log è già stato salvato.");

                    issue.getIssueLogs().add(issueLog);
                });

        return issue;
    }
}
