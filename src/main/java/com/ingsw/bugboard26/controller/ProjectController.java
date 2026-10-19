package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.entities.Project;
import com.ingsw.bugboard26.entities.TeamMember;
import com.ingsw.bugboard26.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/project")
public class ProjectController
{
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Project>> getAllProjects()
    {
        logger.info("****** Otteniamo tutti i progetti *******");

        List<Project> projects = projectService.selectAllProjects();

        if (projects.isEmpty())
            return new ResponseEntity<List<Project>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", projects.size());

        return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
    }

    @GetMapping(value = "/idproject/{idproject}", produces = "application/json")
    public ResponseEntity<Project> getProjectByIdProject(@PathVariable("idproject") int idProject)
    {
        logger.info("****** Cerchiamo il progetto *******");

        Project project = projectService.selecrByIdProject(idProject);

        if (project == null)
            return new ResponseEntity<Project>(HttpStatus.NO_CONTENT);

        logger.info("Progetto trovato.");

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
}
