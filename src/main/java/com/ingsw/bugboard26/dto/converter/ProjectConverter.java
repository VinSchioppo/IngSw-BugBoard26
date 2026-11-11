package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.entity.Project;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ProjectConverter
{
    private ProjectConverter() {}

    public static ProjectDto convertToDto(Project project, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(project == null)
            throw new NotFoundException("Progetto non trovato.");

        return modelMapper.map(project, ProjectDto.class);
    }

    public static List<ProjectDto> convertToDto(List<Project> projects, ModelMapper modelMapper)
            throws NotFoundException
    {
        return projects
                .stream()
                .map(project -> convertToDto(project, modelMapper))
                .toList();
    }

    public static ProjectIssuesDto convertToIssuesDto(Project project, ModelMapper modelMapper)
            throws NotFoundException
    {
        if(project == null)
            throw new NotFoundException("Progetto non trovato.");

        return modelMapper.map(project, ProjectIssuesDto.class);
    }

    public static List<ProjectIssuesDto> convertToIssuesDto(List<Project> projects, ModelMapper modelMapper)
            throws NotFoundException
    {
        return projects
                .stream()
                .map(project -> convertToIssuesDto(project, modelMapper))
                .toList();
    }

    public static Project covertFromDto(ProjectDto projectDto, ModelMapper modelMapper)
    {
        return modelMapper.map(projectDto, Project.class);
    }

    public static Project convertFromIssuesDto(Project project, ProjectIssuesDto projectDto, ModelMapper modelMapper)
            throws NotFoundException, MissingFieldsException
    {
        if(project == null)
            throw new NotFoundException("Progetto non trovato.");

        Project tmpProject = modelMapper.map(projectDto, Project.class);
        tmpProject.getIssues().forEach(
                issue -> {
                    if(issue.getTitle() == null || issue.getTitle().isEmpty() ||
                            issue.getDescription() == null || issue.getDescription().isEmpty())
                        throw new MissingFieldsException("Uno o più campi richiesti sono vuoti.");

                    issue.setIdIssue(null);
                    issue.setProject(project);
                    issue.setStatus(false);
                    issue.getTags().forEach(tag -> tag.setIssue(issue));
                    issue.getImages().forEach(image -> image.setIssue(issue));
                    project.getIssues().add(issue);
                });

        return project;
    }
}
