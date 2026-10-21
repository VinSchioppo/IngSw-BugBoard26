package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.repository.TeamMemberRepository;
import com.ingsw.bugboard26.service.converter.TeamMemberConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamMemberServiceImpl implements TeamMemberService
{
    private final TeamMemberRepository teamMemberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamMemberServiceImpl(final TeamMemberRepository teamMemberRepository,
                                 final ModelMapper modelMapper)
    {
        this.teamMemberRepository = teamMemberRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TeamMemberInfoDto> selectAll()
    {
        return TeamMemberConverter.convertToInfoDto(
                teamMemberRepository.findAll(), modelMapper);
    }

    @Override
    public TeamMemberDto selectByUsername(String username)
    {
        return TeamMemberConverter.convertToDto(
                teamMemberRepository.findByUsername(username), modelMapper);
    }

    @Override
    public TeamMemberIssuesDto selectIssuesByUsername(String username) {
        return TeamMemberConverter.convertToIssueDto(
                teamMemberRepository.findByUsername(username), modelMapper);
    }

    @Override
    public TeamMemberCommentsDto selectCommentsByUsername(String username) {
        return TeamMemberConverter.convertToCommentsDto(
                teamMemberRepository.findByUsername(username), modelMapper);
    }

    @Override
    public TeamMemberNotificationsDto selectNotificationsByUsername(String username) {
        return TeamMemberConverter.convertToNotificationsDto(
                teamMemberRepository.findByUsername(username), modelMapper);
    }
}
