package com.ingsw.bugboard26.service.project;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.exception.MissingFieldsException;
import com.ingsw.bugboard26.exception.NotFoundException;

import java.util.List;

public interface ProjectService
{
    List<ProjectDto> selectAllProjects();
    ProjectIssuesDto selectByIdProject(int idProject);

    void insertProject(ProjectDto projectDto) throws MissingFieldsException;
    void insertIssues(int idProject, ProjectIssuesDto issues) throws MissingFieldsException, NotFoundException;

    void updateProject(int idProject, ProjectDto projectDto) throws MissingFieldsException, NotFoundException;

    void deleteProject(int idProject) throws NotFoundException;
}
