package com.ingsw.bugboard26.service.teamadmin;

import com.ingsw.bugboard26.dto.teamadmin.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.NotFoundException;
import com.ingsw.bugboard26.repository.TeamAdminRepository;
import com.ingsw.bugboard26.dto.converter.TeamAdminConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamAdminServiceImpl implements TeamAdminService
{
    private final TeamAdminRepository teamAdminRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamAdminServiceImpl(final TeamAdminRepository teamAdminRepository, ModelMapper modelMapper)
    {
        this.teamAdminRepository = teamAdminRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamAdminInfoDto> selectAll()
    {
        return TeamAdminConverter.convertToInfoDto(teamAdminRepository.findAll(), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamAdminDto selectByUsername(String username)
    {
        return TeamAdminConverter.convertToDto(
                teamAdminRepository.findByAccountUsername(username), modelMapper);
    }

    @Override
    public void insertNotifications(String username, TeamAdminDto notifications)
            throws DuplicateException
    {
        teamAdminRepository.saveAndFlush(
                TeamAdminConverter.convertFromNotificationsDto(
                        teamAdminRepository.findByAccountUsername(username), notifications, modelMapper));
    }

    @Override
    public void updateStatusNotifications(String username, TeamAdminDto notifications)
    {
        var teamAdmin = teamAdminRepository.findByAccountUsername(username);
        for(var notification : notifications.getNotifications())
        {
            for(var adminNotification : teamAdmin.getNotifications())
            {
                if(notification.getMemberUsername().equals(adminNotification.getMemberUsername())
                && notification.getDescription().equals(adminNotification.getDescription()))
                    adminNotification.setStatus(true);
            }
        }
        teamAdminRepository.saveAndFlush(teamAdmin);
    }

    @Override
    public void deleteAdmin(String username)
            throws NotFoundException
    {
        var teamAdmin = teamAdminRepository.findByAccountUsername(username);
        if(teamAdmin == null)
            throw new NotFoundException("Il team admin " + username + " non è stato trovato.");
        teamAdminRepository.delete(teamAdmin);
    }

    @Override
    public void deleteNotifications(String username)
            throws NotFoundException
    {
        var teamAdmin = teamAdminRepository.findByAccountUsername(username);
        if(teamAdmin == null)
            throw new NotFoundException("Il team admin " + username + " non è stato trovato.");
        teamAdmin.getNotifications().removeIf(
                notification -> Boolean.TRUE.equals(notification.getStatus()));
        teamAdminRepository.saveAndFlush(teamAdmin);
    }
}
