package com.ingsw.bugboard26.service;

import com.ingsw.bugboard26.entities.Project;
import com.ingsw.bugboard26.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService
{
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(final ProjectRepository projectRepository)
    {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> selectAllProjects()
    {
        return projectRepository.findAll();
    }

    @Override
    public Project selectByIdProject(int idProject)
    {
        return projectRepository.findByIdProject(idProject);
    }
}
