package com.ingsw.bugboard26.service.project;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;

import java.util.List;

public interface ProjectService
{
    List<ProjectDto> selectAllProjects();
    ProjectIssuesDto selectByIdProject(Long idProject) throws NotFoundException;

    void insertProject(ProjectDto projectDto) throws MissingFieldsException;
    void insertIssues(Long idProject, ProjectIssuesDto issues) throws MissingFieldsException, NotFoundException;

    void updateProject(Long idProject, ProjectDto projectDto) throws MissingFieldsException, NotFoundException;

    void deleteProject(Long idProject) throws NotFoundException;
}
