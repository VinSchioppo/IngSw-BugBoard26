package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.service.project.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/project")
public class ProjectController
{
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    @Autowired
    public ProjectController(final ProjectService projectService)
    {
        this.projectService = projectService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<ProjectDto>> getAllProjects()
    {
        logger.info("****** Otteniamo tutti i progetti *******");

        List<ProjectDto> projects = projectService.selectAllProjects();

        if (projects.isEmpty())
            return new ResponseEntity<List<ProjectDto>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}.", projects.size());

        return new ResponseEntity<List<ProjectDto>>(projects, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{idproject}", produces = "application/json")
    public ResponseEntity<ProjectIssuesDto> getProjectByIdProject(@PathVariable("idproject") int idProject)
    {
        logger.info("****** Cerchiamo il progetto *******");

        ProjectIssuesDto project = projectService.selectByIdProject(idProject);

        if (project == null)
            return new ResponseEntity<ProjectIssuesDto>(HttpStatus.NO_CONTENT);

        logger.info("Progetto trovato.");

        return new ResponseEntity<ProjectIssuesDto>(project, HttpStatus.OK);
    }

    @PostMapping(value = "/reportissue/id/{idProject}")
    public ResponseEntity<ProjectIssuesDto> reportIssue(
            @PathVariable("idProject") int idProject, @RequestBody ProjectIssuesDto issue)
    {
        logger.info("****** Segnaliamo una nuova issue *******");

        if(issue == null)
            return new ResponseEntity<ProjectIssuesDto>(HttpStatus.NO_CONTENT);

        projectService.insertIssues(idProject, issue);

        logger.info("****** Issue segnalata con successo. *******");

        return new ResponseEntity<ProjectIssuesDto>(new HttpHeaders(), HttpStatus.CREATED);
    }
}
