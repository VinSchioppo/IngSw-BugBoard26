package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.teamadmin.*;
import com.ingsw.bugboard26.entity.TeamAdmin;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;
import org.modelmapper.ModelMapper;

import java.util.List;

public class TeamAdminConverter
{
    private TeamAdminConverter() {}

    public static TeamAdminDto convertToDto(TeamAdmin teamAdmin, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(teamAdmin == null)
            throw new NotFoundException("Admin non trovato.");

        teamAdmin.getNotifications().removeIf(notification ->
            Boolean.TRUE.equals(notification.getStatus()));

        return modelMapper.map(teamAdmin, TeamAdminDto.class);
    }

    public static List<TeamAdminDto> convertToDto(List<TeamAdmin> teamAdmins, ModelMapper modelMapper)
            throws NotFoundException
    {
        return teamAdmins
                .stream()
                .map(teamAdmin -> convertToDto(teamAdmin, modelMapper))
                .toList();
    }

    public static TeamAdminInfoDto convertToInfoDto(TeamAdmin teamAdmin, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(teamAdmin == null)
            throw new NotFoundException("Admin non trovato.");

        return modelMapper.map(teamAdmin, TeamAdminInfoDto.class);
    }

    public static List<TeamAdminInfoDto> convertToInfoDto(List<TeamAdmin> teamAdmins, ModelMapper modelMapper)
            throws NotFoundException
    {
        return teamAdmins
                .stream()
                .map(teamAdmin -> convertToInfoDto(teamAdmin, modelMapper))
                .toList();
    }


    public static TeamAdmin convertFromNotificationsDto(
            TeamAdmin teamAdmin, TeamAdminDto teamAdminDto, ModelMapper modelMapper)
            throws MissingFieldsException, NotFoundException, DuplicateException
    {
        if(teamAdmin == null)
            throw new NotFoundException("Admin non trovato.");

        TeamAdmin tmpAdmin = modelMapper.map(teamAdminDto, TeamAdmin.class);
        tmpAdmin.getNotifications().forEach(
                notification -> {
                    if(notification.getDescription() == null || notification.getDescription().isEmpty() ||
                    notification.getMemberUsername() == null || notification.getMemberUsername().isEmpty())
                        throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

                    notification.setAdminUsername(teamAdmin.getUsername());
                    notification.setStatus(false);

                    if(teamAdmin.getNotifications().contains(notification))
                        throw new DuplicateException("Questa notifica è già stata inviata.");

                    teamAdmin.getNotifications().add(notification);
                });

        return teamAdmin;
    }
}
