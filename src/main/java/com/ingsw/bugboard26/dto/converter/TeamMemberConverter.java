package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.entities.TeamMember;
import org.modelmapper.ModelMapper;

import java.util.List;

public class TeamMemberConverter
{
    TeamMemberConverter() {}

    public static TeamMemberDto convertToDto(TeamMember teamMember, ModelMapper modelMapper)
    {
        TeamMemberDto teamMemberDto = null;

        if (teamMember != null)
        {
            teamMemberDto =  modelMapper.map(teamMember, TeamMemberDto.class);
        }

        return teamMemberDto;
    }

    public static List<TeamMemberDto> convertToDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
    {
        return teamMembers
                .stream()
                .map(source -> convertToDto(source, modelMapper))
                .toList();
    }

    public static TeamMemberInfoDto convertToInfoDto(TeamMember teamMember, ModelMapper modelMapper)
    {
        TeamMemberInfoDto teamMemberDto = null;

        if (teamMember != null)
        {
            teamMemberDto =  modelMapper.map(teamMember, TeamMemberInfoDto.class);
            teamMemberDto.setAdmin(teamMember.getAdminAccount() != null);
        }

        return teamMemberDto;
    }

    public static List<TeamMemberInfoDto> convertToInfoDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
    {
        return teamMembers
                .stream()
                .map(source -> convertToInfoDto(source, modelMapper))
                .toList();
    }

    public static TeamMemberCommentsDto convertToCommentsDto(TeamMember teamMember, ModelMapper modelMapper)
    {
        TeamMemberCommentsDto teamMemberDto = null;

        if (teamMember != null)
        {
            teamMemberDto =  modelMapper.map(teamMember, TeamMemberCommentsDto.class);
        }

        return teamMemberDto;
    }

    public static List<TeamMemberCommentsDto> convertToCommentsDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
    {
        return teamMembers
                .stream()
                .map(source -> convertToCommentsDto(source, modelMapper))
                .toList();
    }

    public static TeamMemberIssuesDto convertToIssueDto(TeamMember teamMember, ModelMapper modelMapper)
    {
        TeamMemberIssuesDto teamMemberDto = null;

        if (teamMember != null)
        {
            teamMemberDto =  modelMapper.map(teamMember, TeamMemberIssuesDto.class);
        }

        return teamMemberDto;
    }

    public static List<TeamMemberIssuesDto> convertToIssuesDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
    {
        return teamMembers
                .stream()
                .map(source -> convertToIssueDto(source, modelMapper))
                .toList();
    }

    public static TeamMemberNotificationsDto convertToNotificationsDto(TeamMember teamMember, ModelMapper modelMapper)
    {
        TeamMemberNotificationsDto teamMemberDto = null;

        if (teamMember != null)
        {
            teamMemberDto =  modelMapper.map(teamMember, TeamMemberNotificationsDto.class);
        }

        return teamMemberDto;
    }

    public static List<TeamMemberNotificationsDto> convertToNotificationsDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
    {
        return teamMembers
                .stream()
                .map(source -> convertToNotificationsDto(source, modelMapper))
                .toList();
    }

    public static TeamMember convertFromNotificationsDto(
            TeamMember teamMember, TeamMemberNotificationsDto teamMemberDto, ModelMapper modelMapper)
    {
        TeamMember tmpMember = null;

        if (teamMember != null && teamMemberDto != null)
        {
            tmpMember =  modelMapper.map(teamMemberDto, TeamMember.class);
            tmpMember.getNotifications().forEach(
                    source -> {
                        source.setMemberUsername(teamMember.getUsername());
                        source.setStatus(false);
                        teamMember.addNotification(source);
                    });
        }

        return teamMember;
    }
}
