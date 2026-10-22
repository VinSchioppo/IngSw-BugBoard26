package com.ingsw.bugboard26.dto.converter;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.entities.Project;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ProjectConverter
{
    private ProjectConverter() {}

    public static ProjectDto convertToDto(Project project, ModelMapper modelMapper)
    {
        ProjectDto projectDto = null;

        if (project != null)
        {
            projectDto =  modelMapper.map(project, ProjectDto.class);
        }

        return projectDto;
    }

    public static List<ProjectDto> convertToDto(List<Project> projects, ModelMapper modelMapper)
    {
        return projects
                .stream()
                .map(source -> convertToDto(source, modelMapper))
                .toList();
    }

    public static ProjectIssuesDto convertToIssuesDto(Project project, ModelMapper modelMapper)
    {
        ProjectIssuesDto projectDto = null;

        if (project != null)
        {
            projectDto =  modelMapper.map(project, ProjectIssuesDto.class);
        }

        return projectDto;
    }

    public static List<ProjectIssuesDto> convertToIssuesDto(List<Project> projects, ModelMapper modelMapper)
    {
        return projects
                .stream()
                .map(source -> convertToIssuesDto(source, modelMapper))
                .toList();
    }
}
