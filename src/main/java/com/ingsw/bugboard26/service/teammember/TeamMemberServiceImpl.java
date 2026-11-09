package com.ingsw.bugboard26.service.teammember;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.NotFoundException;
import com.ingsw.bugboard26.repository.TeamMemberRepository;
import com.ingsw.bugboard26.dto.converter.TeamMemberConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(rollbackFor = {
        NotFoundException.class,
        DuplicateException.class
})
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
    @Transactional(readOnly = true)
    public List<TeamMemberInfoDto> selectAll()
    {
        return TeamMemberConverter.convertToInfoDto(
                teamMemberRepository.findAll(), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamMemberDto selectByUsername(String username)
    {
        return TeamMemberConverter.convertToDto(
                teamMemberRepository.findByUsername(username), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamMemberIssuesDto selectIssuesByUsername(String username) {
        return TeamMemberConverter.convertToIssueDto(
                teamMemberRepository.findByUsername(username), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamMemberCommentsDto selectCommentsByUsername(String username) {
        return TeamMemberConverter.convertToCommentsDto(
                teamMemberRepository.findByUsername(username), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamMemberNotificationsDto selectNotificationsByUsername(String username) {
        return TeamMemberConverter.convertToNotificationsDto(
                teamMemberRepository.findByUsername(username), modelMapper);
    }

    @Override
    public void insertTeamMembers(List<TeamMemberInfoDto> teamMembers)
            throws DuplicateException
    {
        List<String> usernames = new ArrayList<>();
        teamMembers.forEach(teamMember ->
                usernames.add(teamMember.getUsername()));

        Set<String> usernamesSet = new HashSet<>(usernames);

        if(usernames.size() > usernamesSet.size())
            throw new DuplicateException("Tentativo di inserire due utenti con lo stesso username.");

        if(!teamMemberRepository.findAllByUsernameIn(usernames).isEmpty())
            throw new DuplicateException("Nome utente duplicato.");

        teamMemberRepository.saveAll(
                TeamMemberConverter.convertFromInfoDto(teamMembers, modelMapper));
    }

    @Override
    public void insertComments(String username, TeamMemberCommentsDto comments)
            throws NotFoundException, DuplicateException
    {
        teamMemberRepository.saveAndFlush(
                TeamMemberConverter.convertFromCommentsDto(
                        teamMemberRepository.findByUsername(username), comments, modelMapper));
    }

    @Override
    public void insertNotifications(String username, TeamMemberNotificationsDto notifications)
            throws NotFoundException, DuplicateException
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
    public void updateToAdmin(List<TeamMemberInfoDto> teamMembersDto) throws NotFoundException
    {
        teamMembersDto.forEach( teamMember ->
                teamMemberRepository.saveAndFlush(
                        TeamMemberConverter.convertToAdmin(teamMemberRepository.findByUsername(teamMember.getUsername()))));
    }

    @Override
    public void updateStatusNotifications(String username, TeamMemberNotificationsDto notifications)
    {
        var teamMember = teamMemberRepository.findByUsername(username);
        for(var notification : notifications.getNotifications())
        {
            for(var memberNotification : teamMember.getNotifications())
            {
                if(notification.getAdminUsername().equals(memberNotification.getAdminUsername())
                        && notification.getDescription().equals(memberNotification.getDescription()))
                    memberNotification.setStatus(true);
            }
        }
        teamMemberRepository.saveAndFlush(teamMember);
    }

    @Override
    public void deleteMember(String username)
            throws NotFoundException
    {
        var teamMember = teamMemberRepository.findByUsername(username);
        if(teamMember == null)
            throw new NotFoundException("L'utente " + username + " non è stato trovato.");
        teamMemberRepository.delete(teamMember);
    }

    @Override
    public void deleteNotifications(String username)
            throws NotFoundException
    {
        var teamMember = teamMemberRepository.findByUsername(username);
        if(teamMember == null)
            throw new NotFoundException("L'utente " + username + " non è stato trovato.");

        teamMember.getNotifications().removeIf(
                notification -> Boolean.TRUE.equals(notification.getStatus()));
        teamMemberRepository.saveAndFlush(teamMember);
    }
}
