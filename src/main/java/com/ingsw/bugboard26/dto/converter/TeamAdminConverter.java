package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.teamadmin.*;
import com.ingsw.bugboard26.entity.TeamAdmin;
import com.ingsw.bugboard26.exception.DuplicateException;
import org.modelmapper.ModelMapper;

import java.util.List;

public class TeamAdminConverter
{
    private TeamAdminConverter() {}

    public static TeamAdminDto convertToDto(TeamAdmin teamAdmin, ModelMapper modelMapper)
    {
        TeamAdminDto teamAdminDto = null;

        if (teamAdmin != null)
        {
            teamAdmin.getNotifications().removeIf(notification ->
                    Boolean.TRUE.equals(notification.getStatus()));
            teamAdminDto = modelMapper.map(teamAdmin, TeamAdminDto.class);
        }
        return teamAdminDto;
    }

    public static List<TeamAdminDto> convertToDto(List<TeamAdmin> teamAdmins, ModelMapper modelMapper)
    {
        return teamAdmins
                .stream()
                .map(teamAdmin -> convertToDto(teamAdmin, modelMapper))
                .toList();
    }

    public static TeamAdminInfoDto convertToInfoDto(TeamAdmin teamAdmin, ModelMapper modelMapper)
    {
        TeamAdminInfoDto teamAdminDto = null;

        if (teamAdmin != null)
            teamAdminDto = modelMapper.map(teamAdmin, TeamAdminInfoDto.class);
        return teamAdminDto;
    }

    public static List<TeamAdminInfoDto> convertToInfoDto(List<TeamAdmin> teamAdmins, ModelMapper modelMapper)
    {
        return teamAdmins
                .stream()
                .map(teamAdmin -> convertToInfoDto(teamAdmin, modelMapper))
                .toList();
    }


    public static TeamAdmin convertFromNotificationsDto(
            TeamAdmin teamAdmin, TeamAdminDto teamAdminDto, ModelMapper modelMapper)
            throws DuplicateException
    {
        if (teamAdmin != null && teamAdminDto != null)
        {
            TeamAdmin tmpAdmin = modelMapper.map(teamAdminDto, TeamAdmin.class);
            tmpAdmin.getNotifications().forEach(
                    notification -> {
                        notification.setAdminUsername(teamAdmin.getUsername());
                        notification.setStatus(false);

                        if(teamAdmin.getNotifications().contains(notification))
                            throw new DuplicateException("Questa notifica è già stata inviata.");

                        teamAdmin.addNotification(notification);
                    });
        }

        return teamAdmin;
    }
}
