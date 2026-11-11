package com.ingsw.bugboard26.service.project;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;
import com.ingsw.bugboard26.repository.ProjectRepository;
import com.ingsw.bugboard26.dto.converter.ProjectConverter;
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
    @Transactional(readOnly = true)
    public List<ProjectDto> selectAllProjects()
    {
        return ProjectConverter.convertToDto(projectRepository.findAll(), modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectIssuesDto selectByIdProject(Long idProject)
            throws NotFoundException
    {
        return ProjectConverter.convertToIssuesDto(
                projectRepository.findByIdProject(idProject), modelMapper);
    }

    @Override
    public void insertProject(ProjectDto projectDto)
            throws MissingFieldsException
    {
        if(projectDto == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if(projectDto.getTitle() == null || projectDto.getTitle().isEmpty() ||
            projectDto.getDescription() == null || projectDto.getDescription().isEmpty())
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        projectRepository.saveAndFlush(
                ProjectConverter.covertFromDto(projectDto, modelMapper));
    }

    @Override
    public void insertIssues(Long idProject, ProjectIssuesDto issues)
            throws MissingFieldsException, NotFoundException
    {
        if(issues == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");

        if((issues.getIssues() == null || issues.getIssues().isEmpty()))
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        projectRepository.saveAndFlush(
                ProjectConverter.convertFromIssuesDto(
                        projectRepository.findByIdProject(idProject), issues, modelMapper));
    }

    @Override
    public void updateProject(Long idProject, ProjectDto projectDto)
            throws MissingFieldsException, NotFoundException
    {
        if(projectDto == null)
            throw new MissingFieldsException("È necessario un body per esegire questa richiesta.");
        if((projectDto.getTitle() == null || projectDto.getTitle().isEmpty()) &&
        (projectDto.getDescription() == null || projectDto.getDescription().isEmpty()))
            throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

        String title = projectDto.getTitle();
        String description = projectDto.getDescription();
        var project = projectRepository.findByIdProject(idProject);
        if(title != null && !title.isEmpty())
            project.setTitle(title);
        if(description != null && !description.isEmpty())
            project.setDescription(description);
        projectRepository.saveAndFlush(project);
    }

    @Override
    public void deleteProject(Long idProject)
            throws NotFoundException
    {
        var project = projectRepository.findByIdProject(idProject);
        if(project == null)
            throw new NotFoundException("Il progetto con id " + idProject + " non è stato trovato.");
        projectRepository.delete(project);
    }
}
