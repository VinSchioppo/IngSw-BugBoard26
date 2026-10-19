package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.Project;

import java.util.List;

public interface ProjectService
{
    List<Project> selectAllProjects();
    Project selecrByIdProject(int idProject);
}
