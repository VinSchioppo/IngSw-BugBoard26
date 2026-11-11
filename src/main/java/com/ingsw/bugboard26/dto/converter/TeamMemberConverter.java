package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.entity.TeamAdmin;
import com.ingsw.bugboard26.entity.TeamMember;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TeamMemberConverter
{
    private TeamMemberConverter() {}

    public static TeamMemberDto convertToDto(TeamMember teamMember, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        return modelMapper.map(teamMember, TeamMemberDto.class);
    }

    public static List<TeamMemberDto> convertToDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
            throws NotFoundException
    {
        return teamMembers
                .stream()
                .map(teamMember -> convertToDto(teamMember, modelMapper))
                .toList();
    }

    public static TeamMemberInfoDto convertToInfoDto(TeamMember teamMember, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        TeamMemberInfoDto teamMemberDto =  modelMapper.map(teamMember, TeamMemberInfoDto.class);
        teamMemberDto.setIsAdmin(teamMember.getAdminAccount() != null);

        return teamMemberDto;
    }

    public static List<TeamMemberInfoDto> convertToInfoDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
            throws NotFoundException
    {
        return teamMembers
                .stream()
                .map(teamMember -> convertToInfoDto(teamMember, modelMapper))
                .toList();
    }

    public static TeamMemberCommentsDto convertToCommentsDto(TeamMember teamMember, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        return modelMapper.map(teamMember, TeamMemberCommentsDto.class);
    }

    public static List<TeamMemberCommentsDto> convertToCommentsDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
            throws NotFoundException
    {
        return teamMembers
                .stream()
                .map(teamMember -> convertToCommentsDto(teamMember, modelMapper))
                .toList();
    }

    public static TeamMemberIssuesDto convertToIssueDto(TeamMember teamMember, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        return modelMapper.map(teamMember, TeamMemberIssuesDto.class);
    }

    public static List<TeamMemberIssuesDto> convertToIssuesDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
            throws NotFoundException
    {
        return teamMembers
                .stream()
                .map(teamMember -> convertToIssueDto(teamMember, modelMapper))
                .toList();
    }

    public static TeamMemberNotificationsDto convertToNotificationsDto(TeamMember teamMember, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        teamMember.getNotifications().removeIf(notification ->
                Boolean.TRUE.equals(notification.getStatus()));

        return modelMapper.map(teamMember, TeamMemberNotificationsDto.class);
    }

    public static List<TeamMemberNotificationsDto> convertToNotificationsDto(List<TeamMember> teamMembers, ModelMapper modelMapper)
            throws NotFoundException
    {
        return teamMembers
                .stream()
                .map(teamMember -> convertToNotificationsDto(teamMember, modelMapper))
                .toList();
    }

    public static TeamMember convertFromInfoDto(TeamMemberInfoDto teamMemberDto, ModelMapper modelMapper)
    {
        TeamMember teamMember = modelMapper.map(teamMemberDto, TeamMember.class);
        teamMember.setPassword(teamMemberDto.getPassword());
        if(Boolean.TRUE.equals(teamMemberDto.getIsAdmin()))
        {
            teamMember.setAdminAccount(new TeamAdmin());
            teamMember.getAdminAccount().setAccount(teamMember);
            teamMember.getAdminAccount().setUsername(teamMember.getUsername());
        }

        return teamMember;
    }

    public static List<TeamMember> convertFromInfoDto(List<TeamMemberInfoDto> teamMembersDto, ModelMapper modelMapper)
    {
        List<TeamMember> teamMembers = new ArrayList<>();

        teamMembersDto.forEach(
                teamMemberDto ->
                        teamMembers.add(convertFromInfoDto(teamMemberDto, modelMapper)));

        return teamMembers;
    }

    public static TeamMember convertFromCommentsDto(
            TeamMember teamMember, TeamMemberCommentsDto teamMemberDto, ModelMapper modelMapper)
            throws MissingFieldsException, NotFoundException, DuplicateException
    {
        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        TeamMember tmpMember =  modelMapper.map(teamMemberDto, TeamMember.class);
        tmpMember.getComments().forEach(
                issueComment -> {
                    if(issueComment.getIdIssue() == null || issueComment.getIdIssue() == 0L ||
                            issueComment.getDescription() == null || issueComment.getDescription().isEmpty())
                        throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

                    issueComment.setMemberUsername(teamMember.getUsername());
                    issueComment.setDate(LocalDate.now(ZoneId.systemDefault()));

                    if(teamMember.getComments().contains(issueComment))
                        throw new DuplicateException("Questo commento è già stato postato.");
                    teamMember.getComments().add(issueComment);
                });

        return teamMember;
    }

    public static TeamMember convertFromNotificationsDto(
            TeamMember teamMember, TeamMemberNotificationsDto teamMemberDto, ModelMapper modelMapper)
            throws MissingFieldsException, NotFoundException, DuplicateException
    {
        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        TeamMember tmpMember =  modelMapper.map(teamMemberDto, TeamMember.class);
        tmpMember.getNotifications().forEach(
                notification -> {
                    if(notification.getAdminUsername() == null || notification.getAdminUsername().isEmpty() ||
                            notification.getDescription() == null || notification.getDescription().isEmpty())
                        throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");


                    notification.setMemberUsername(teamMember.getUsername());
                    notification.setStatus(false);

                    if(teamMember.getNotifications().contains(notification))
                        throw new DuplicateException("Questa notifica è già stata inviata.");

                    teamMember.getNotifications().add(notification);
                });

        return teamMember;
    }

    public static TeamMember convertToAdmin(TeamMember teamMember)
            throws NotFoundException
    {
        if (teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        teamMember.setAdminAccount(new TeamAdmin());
        teamMember.getAdminAccount().setAccount(teamMember);
        teamMember.getAdminAccount().setUsername(teamMember.getUsername());

        return teamMember;
    }
}
