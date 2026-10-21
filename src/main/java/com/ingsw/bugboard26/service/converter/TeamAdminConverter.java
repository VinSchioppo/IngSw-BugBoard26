package com.ingsw.bugboard26.service.converter;

import com.ingsw.bugboard26.dto.teamadmin.TeamAdminDto;
import com.ingsw.bugboard26.entities.TeamAdmin;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class TeamAdminConverter
{
    private TeamAdminConverter() {}

    public static TeamAdminDto convertToDto(TeamAdmin teamAdmin, ModelMapper modelMapper)
    {
        TeamAdminDto teamAdminDto = null;

        if (teamAdmin != null)
        {
            teamAdminDto =  modelMapper.map(teamAdmin, TeamAdminDto.class);
        }

        return teamAdminDto;
    }

    public static List<TeamAdminDto> convertToDto(List<TeamAdmin> teamAdmins, ModelMapper modelMapper)
    {
        return teamAdmins
                .stream()
                .map(source -> convertToDto(source, modelMapper))
                .toList();
    }
}
