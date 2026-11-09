package com.ingsw.bugboard26.service.project;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.exception.NotFoundException;

import java.util.List;

public interface ProjectService
{
    List<ProjectDto> selectAllProjects();
    ProjectIssuesDto selectByIdProject(int idProject);

    void insertProject(ProjectDto projectDto);
    void insertIssues(int idProject, ProjectIssuesDto issues);

    void updateProject(int idProject, ProjectDto projectDto);

    void deleteProject(int idProject) throws NotFoundException;
}
