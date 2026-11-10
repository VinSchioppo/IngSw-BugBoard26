package com.ingsw.bugboard26.service.issue;

import com.ingsw.bugboard26.dto.issue.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;
import com.ingsw.bugboard26.repository.IssueRepository;
import com.ingsw.bugboard26.dto.converter.IssueConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {
        NotFoundException.class,
        DuplicateException.class,
        MissingFieldsException.class,
})
public class IssueServiceImpl implements IssueService
{
    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IssueServiceImpl(final IssueRepository issueRepository, ModelMapper modelMapper)
    {
        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public IssueDto selectByIdIssue(int idIssue)
    {

        return IssueConverter.convertToDto(
                issueRepository.findByIdIssue(idIssue), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public IssueCommentsDto selectCommentsByIdIssue(int idIssue) {
        return IssueConverter.convertToCommentsDto(
                issueRepository.findByIdIssue(idIssue), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public IssueLogsDto selectLogsByIdIssue(int idIssue) {
        return IssueConverter.convertToLogsDto(
                issueRepository.findByIdIssue(idIssue), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueInfoDto> selectByMemberUsername(String username)
    {
        return IssueConverter.convertToInfoDto(
                issueRepository.findByTeamMemberUsername(username), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueInfoDto> selectByTag(String nameTag)
    {
        return IssueConverter.convertToInfoDto(
                issueRepository.findByTagsNameTag(nameTag), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueInfoDto> selectByPriority(int priority)
    {
        return IssueConverter.convertToInfoDto(
                issueRepository.findByPriority(priority), modelMapper);
    }

    @Override
    public void insertMember(int idIssue, IssueMemberDto teamMember)
            throws MissingFieldsException, NotFoundException
    {
        if(teamMember == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(teamMember.getTeamMember() == null || teamMember.getTeamMember().getUsername() == null)
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        issueRepository.saveAndFlush(
                IssueConverter.convertFromMemberDto(
                        issueRepository.findByIdIssue(idIssue), teamMember, modelMapper));
    }

    @Override
    public void insertLogs(int idIssue, IssueLogsDto logs)
            throws MissingFieldsException, NotFoundException, DuplicateException
    {
        if(logs == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(logs.getIssueLogs() == null || logs.getIssueLogs().isEmpty())
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        issueRepository.saveAndFlush(
                IssueConverter.convertFromLogsDto(
                        issueRepository.findByIdIssue(idIssue), logs, modelMapper));
    }

    @Override
    public void updateStatus(int idIssue, IssueInfoDto issueInfoDto)
            throws MissingFieldsException, NotFoundException
    {
        if(issueInfoDto == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(issueInfoDto.getStatus() == null)
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        var issue = issueRepository.findByIdIssue(idIssue);

        if(issue == null)
            throw new NotFoundException("Issue non trovata.");

        issue.setStatus(issueInfoDto.getStatus());
        issueRepository.saveAndFlush(issue);
    }

    @Override
    public void deleteIssue(int idIssue)
            throws NotFoundException
    {
        var issue = issueRepository.findByIdIssue(idIssue);

        if(issue == null)
            throw new NotFoundException("Issue non trovata.");

        issueRepository.delete(issue);
    }

    @Override
    public String adviceMember()
    {
        List<Object[]> countAssignments = issueRepository.findIssuesAssigned();
        if(!countAssignments.isEmpty())
            return "{\"username\":\"" + countAssignments.get(0)[0].toString() + "\"}";
        return null;
    }
}
