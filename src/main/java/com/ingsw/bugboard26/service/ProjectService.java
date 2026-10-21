package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.dto.project.*;

import java.util.List;

public interface ProjectService
{
    List<ProjectDto> selectAllProjects();
    ProjectIssuesDto selectByIdProject(int idProject);
}
