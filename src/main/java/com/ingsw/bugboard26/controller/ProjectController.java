package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.project.*;
import com.ingsw.bugboard26.service.project.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;

@RestController
@RequestMapping(value = "api/projects")
public class ProjectController
{
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    @Autowired
    public ProjectController(final ProjectService projectService)
    {
        this.projectService = projectService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProjectDto>> getAllProjects()
    {
        logger.info("****** Otteniamo tutti i progetti *******");

        List<ProjectDto> projects = projectService.selectAllProjects();

        if (projects.isEmpty())
            return new ResponseEntity<List<ProjectDto>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}.", projects.size());

        return new ResponseEntity<List<ProjectDto>>(projects, HttpStatus.OK);
    }

    @GetMapping(value = "/{idproject}", produces = "application/json")
    public ResponseEntity<ProjectIssuesDto> getProjectByIdProject(@PathVariable("idproject") int idProject)
    {
        logger.info("****** Cerchiamo il progetto *******");

        ProjectIssuesDto project = projectService.selectByIdProject(idProject);

        if (project == null)
            return new ResponseEntity<ProjectIssuesDto>(HttpStatus.NO_CONTENT);

        logger.info("Progetto trovato.");

        return new ResponseEntity<ProjectIssuesDto>(project, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto project)
    {
        logger.info("****** Creiamo un nuovo progetto *******");

        if(project == null)
            return new ResponseEntity<ProjectDto>(HttpStatus.NO_CONTENT);

        projectService.insertProject(project);

        logger.info("Progetto creato con successo.");

        return new ResponseEntity<ProjectDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/issues/{idProject}")
    public ResponseEntity<ProjectIssuesDto> reportIssue(
            @PathVariable("idProject") int idProject, @RequestBody ProjectIssuesDto issue)
    {
        logger.info("****** Segnaliamo una nuova issue *******");

        if(issue == null)
            return new ResponseEntity<ProjectIssuesDto>(HttpStatus.NO_CONTENT);

        projectService.insertIssues(idProject, issue);

        logger.info("Issue segnalata con successo.");

        return new ResponseEntity<ProjectIssuesDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{idProject}")
    public ResponseEntity<ProjectDto> changeInfo(
            @PathVariable("idProject") int idProject, @RequestBody ProjectDto projectDto)
    {
        logger.info("****** Aggiorniamo le informazioni del progetto *******");

        if(projectDto == null)
            return new ResponseEntity<ProjectDto>(HttpStatus.NO_CONTENT);

        projectService.updateProject(idProject, projectDto);

        logger.info("Aggiornamento eseguito con successo.");

        return new ResponseEntity<ProjectDto>(new HttpHeaders(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{idProject}")
    public ResponseEntity<?> removeProject(@PathVariable("idProject") int idProject)
    {
        logger.info("Eliminiamo il progetto con id {}", idProject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus httpStatus = HttpStatus.OK;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        if(projectService.deleteProject(idProject))
        {
            responseNode.put("code", HttpStatus.OK.toString());
            responseNode.put("message", "Eliminazione progetto " + idProject + " eseguita con successo!!");
        }
        else
        {
            responseNode.put("code", HttpStatus.NOT_FOUND.toString());
            responseNode.put("message", "Progetto non trovata.");
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(responseNode, headers, httpStatus);
    }
}
