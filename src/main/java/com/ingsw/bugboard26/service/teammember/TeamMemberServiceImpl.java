package com.ingsw.bugboard26.service.teammember;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
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
        DuplicateException.class,
        MissingFieldsException.class,
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
            throws MissingFieldsException, DuplicateException
    {
        if(teamMembers == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(teamMembers.isEmpty())
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        List<String> usernames = new ArrayList<>();
        for(TeamMemberInfoDto teamMember : teamMembers)
        {
            if((teamMember.getUsername() == null || teamMember.getUsername().isEmpty()) ||
            (teamMember.getEmail() == null || teamMember.getEmail().isEmpty()) ||
            (teamMember.getPassword() == null || teamMember.getPassword().isEmpty()))
                throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

            usernames.add(teamMember.getUsername());
        }

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
            throws MissingFieldsException, NotFoundException, DuplicateException
    {
        if(comments == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(comments.getComments() == null || comments.getComments().isEmpty())
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        teamMemberRepository.saveAndFlush(
                TeamMemberConverter.convertFromCommentsDto(
                        teamMemberRepository.findByUsername(username), comments, modelMapper));
    }

    @Override
    public void insertNotifications(String username, TeamMemberNotificationsDto notifications)
            throws MissingFieldsException, NotFoundException, DuplicateException
    {
        if(notifications == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(notifications.getNotifications() == null || notifications.getNotifications().isEmpty())
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        teamMemberRepository.saveAndFlush(
                TeamMemberConverter.convertFromNotificationsDto(
                        teamMemberRepository.findByUsername(username), notifications, modelMapper));
    }

    @Override
    public void updatePassword(String username, TeamMemberInfoDto teamMemberDto)
            throws MissingFieldsException, NotFoundException
    {
        if(teamMemberDto == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(teamMemberDto.getPassword() == null || teamMemberDto.getPassword().isEmpty())
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        var teamMember = teamMemberRepository.findByUsername(username);

        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        teamMember.setPassword(passwordEncoder.encode(teamMemberDto.getPassword()));
        teamMemberRepository.saveAndFlush(teamMember);
    }

    @Override
    public void updateToAdmin(List<TeamMemberInfoDto> teamMembersDto)
            throws MissingFieldsException, NotFoundException
    {
        if(teamMembersDto == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(teamMembersDto.isEmpty())
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        teamMembersDto.forEach( teamMember ->
                teamMemberRepository.saveAndFlush(
                        TeamMemberConverter.convertToAdmin(teamMemberRepository.findByUsername(teamMember.getUsername()))));
    }

    @Override
    public void updateStatusNotifications(String username, TeamMemberNotificationsDto notifications)
            throws MissingFieldsException, NotFoundException
    {
        if(notifications == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(notifications.getNotifications() == null || notifications.getNotifications().isEmpty())
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        var teamMember = teamMemberRepository.findByUsername(username);

        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

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
            throw new NotFoundException("Utente non trovato.");

        teamMemberRepository.delete(teamMember);
    }

    @Override
    public void deleteNotifications(String username)
            throws NotFoundException
    {
        var teamMember = teamMemberRepository.findByUsername(username);

        if(teamMember == null)
            throw new NotFoundException("Utente non trovato.");

        teamMember.getNotifications().removeIf(
                notification -> Boolean.TRUE.equals(notification.getStatus()));
        teamMemberRepository.saveAndFlush(teamMember);
    }
}
