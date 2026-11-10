package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.entity.Project;
import com.ingsw.bugboard26.exception.NotFoundException;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ProjectConverter
{
    private ProjectConverter() {}

    public static ProjectDto convertToDto(Project project, ModelMapper modelMapper)
    {
        ProjectDto projectDto = null;

        if (project != null)
            projectDto =  modelMapper.map(project, ProjectDto.class);

        return projectDto;
    }

    public static List<ProjectDto> convertToDto(List<Project> projects, ModelMapper modelMapper)
    {
        return projects
                .stream()
                .map(project -> convertToDto(project, modelMapper))
                .toList();
    }

    public static ProjectIssuesDto convertToIssuesDto(Project project, ModelMapper modelMapper)
    {
        ProjectIssuesDto projectDto = null;

        if (project != null)
            projectDto =  modelMapper.map(project, ProjectIssuesDto.class);

        return projectDto;
    }

    public static List<ProjectIssuesDto> convertToIssuesDto(List<Project> projects, ModelMapper modelMapper)
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
            throws NotFoundException
    {
        if(project == null)
            throw new NotFoundException("Progetto non trovato.");

        Project tmpProject = modelMapper.map(projectDto, Project.class);
        tmpProject.getIssues().forEach(
                issue -> {
                    issue.setIdIssue(0);
                    issue.setProject(project);
                    issue.setStatus(false);
                    issue.getTags().forEach(tag -> tag.setIssue(issue));
                    issue.getImages().forEach(image -> image.setIssue(issue));
                    project.addIssue(issue);
                });

        return project;
    }
}
