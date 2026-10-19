package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.entities.Issue;
import com.ingsw.bugboard26.service.IssueService;
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
@RequestMapping(value = "api/issue")
public class IssueController
{
    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    private IssueService issueService;

    @GetMapping(value = "/idissue/{idIssue}", produces = "/application/json")
    public ResponseEntity<Issue> getIssueByIdIssue(@PathVariable("idIssue") int idIssue)
    {
        logger.info("****** Cerchiamo l'issue *******");

        Issue issue = issueService.selectByIdIssue(idIssue);

        if (issue == null)
            return new ResponseEntity<Issue>(HttpStatus.NO_CONTENT);

        logger.info("Issue trovata.");

        return new ResponseEntity<Issue>(issue, HttpStatus.OK);
    }

    @GetMapping(value = "/idproject/{idProject}", produces = "/application/json")
    public ResponseEntity<List<Issue>> getIssuesByIdProject(@PathVariable("idProject") int idProject)
    {
        logger.info("****** Cerchiamo le issue per id progetto *******");

        List<Issue> issues = issueService.selectByIdProject(idProject);

        if (issues.isEmpty())
            return new ResponseEntity<List<Issue>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", issues.size());

        return new ResponseEntity<List<Issue>>(issues, HttpStatus.OK);
    }

    @GetMapping(value = "/memberusername/{memberUsername}", produces = "/application/json")
    public ResponseEntity<List<Issue>> getIssuesByMemberUsername(@PathVariable("memberUsername") String memberUsername)
    {
        logger.info("****** Cerchiamo le issues assegnate ad un membro *******");

        List<Issue> issues = issueService.selectByMemberUsername(memberUsername);

        if (issues.isEmpty())
            return new ResponseEntity<List<Issue>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", issues.size());

        return new ResponseEntity<List<Issue>>(issues, HttpStatus.OK);
    }

    @GetMapping(value = "/priority/{priority}", produces = "/application/json")
    public ResponseEntity<List<Issue>> getIssuesByPriority(@PathVariable("priority") int priority)
    {
        logger.info("****** Cerchiamo le issues con priorità {} *******", priority);

        List<Issue> issues = issueService.selectByPriority(priority);

        if (issues.isEmpty())
            return new ResponseEntity<List<Issue>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", issues.size());

        return new ResponseEntity<List<Issue>>(issues, HttpStatus.OK);
    }
}
