package com.ingsw.bugboard26.service.project;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.repository.ProjectRepository;
import com.ingsw.bugboard26.dto.converter.ProjectConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService
{
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(final ProjectRepository projectRepository, ModelMapper modelMapper)
    {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProjectDto> selectAllProjects()
    {
        return ProjectConverter.convertToDto(projectRepository.findAll(), modelMapper);
    }

    @Override
    public ProjectIssuesDto selectByIdProject(int idProject)
    {
        return ProjectConverter.convertToIssuesDto(
                projectRepository.findByIdProject(idProject), modelMapper);
    }
}
