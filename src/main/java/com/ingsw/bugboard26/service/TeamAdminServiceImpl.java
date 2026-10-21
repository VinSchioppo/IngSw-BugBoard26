package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.dto.teamadmin.*;
import com.ingsw.bugboard26.repository.TeamAdminRepository;
import com.ingsw.bugboard26.service.converter.TeamAdminConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public TeamAdminDto selectByUserame(String username)
    {
        return TeamAdminConverter.convertToDto(
                teamAdminRepository.findByAccountUsername(username), modelMapper);
    }
}
