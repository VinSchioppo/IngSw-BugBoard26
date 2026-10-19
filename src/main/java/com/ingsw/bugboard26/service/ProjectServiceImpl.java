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
    @Autowired(required = false)
    private ProjectRepository projectRepository;

    @Override
    public List<Project> selectAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project selecrByIdProject(int idProject) {
        return projectRepository.findByIdProject(idProject);
    }
}
