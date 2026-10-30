package com.ingsw.bugboard26.service.teammember;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.repository.TeamMemberRepository;
import com.ingsw.bugboard26.dto.converter.TeamMemberConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamMemberServiceImpl implements TeamMemberService
{
    private final TeamMemberRepository teamMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamMemberServiceImpl(final TeamMemberRepository teamMemberRepository,
                                 final PasswordEncoder passwordEncoder,
                                 final ModelMapper modelMapper)
    {
        this.teamMemberRepository = teamMemberRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public void insertTeamMembers(List<TeamMemberInfoDto> teamMembers)
    {
        teamMembers.forEach(source -> {
            String password = source.getPassword();
            source.setPassword(passwordEncoder.encode(password));
            teamMemberRepository.saveAndFlush(
                    TeamMemberConverter.convertFromInfoDto(source, modelMapper));
        });

    }

    @Override
    public void insertComments(String username, TeamMemberCommentsDto comments)
    {
        teamMemberRepository.saveAndFlush(
                TeamMemberConverter.convertFromCommentsDto(
                        teamMemberRepository.findByUsername(username), comments, modelMapper));
    }

    @Override
    public void insertNotifications(String username, TeamMemberNotificationsDto notifications)
    {
        teamMemberRepository.saveAndFlush(
                TeamMemberConverter.convertFromNotificationsDto(
                        teamMemberRepository.findByUsername(username), notifications, modelMapper));
    }

    @Override
    public void updatePassword(String username, TeamMemberInfoDto teamMemberDto)
    {
        var teamMember = teamMemberRepository.findByUsername(username);
        teamMember.setPassword(passwordEncoder.encode(teamMemberDto.getPassword()));
        teamMemberRepository.saveAndFlush(teamMember);
    }

    @Override
    public void updateToAdmin(List<TeamMemberInfoDto> teamMembersDto)
    {
        teamMembersDto.forEach( source ->
                teamMemberRepository.saveAndFlush(
                        TeamMemberConverter.convertToAdmin(teamMemberRepository.findByUsername(source.getUsername()))));
    }
}
