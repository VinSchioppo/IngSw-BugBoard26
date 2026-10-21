package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.dto.issue.*;
import com.ingsw.bugboard26.repository.IssueRepository;
import com.ingsw.bugboard26.service.converter.IssueConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    public IssueDto selectByIdIssue(int idIssue)
    {

        return IssueConverter.convertToDto(
                issueRepository.findByIdIssue(idIssue), modelMapper);
    }

    @Override
    public IssueCommentsDto selectCommentsByIdIssue(int idIssue) {
        return IssueConverter.convertToCommentsDto(
                issueRepository.findByIdIssue(idIssue), modelMapper);
    }

    @Override
    public IssueLogsDto selectLogsByIdIssue(int idIssue) {
        return IssueConverter.convertToLogsDto(
                issueRepository.findByIdIssue(idIssue), modelMapper);
    }

    @Override
    public List<IssueInfoDto> selectByMemberUsername(String username)
    {
        return IssueConverter.convertToInfoDto(
                issueRepository.findByTeamMemberUsername(username), modelMapper);
    }

    @Override
    public List<IssueInfoDto> selectByTag(String nameTag)
    {
        return IssueConverter.convertToInfoDto(
                issueRepository.findByTagsNameTag(nameTag), modelMapper);
    }

    @Override
    public List<IssueInfoDto> selectByPriority(int priority)
    {
        return IssueConverter.convertToInfoDto(
                issueRepository.findByPriority(priority), modelMapper);
    }
}
