package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.issue.*;
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

    private final IssueService issueService;

    @Autowired
    public IssueController(final IssueService issueService)
    {
        this.issueService = issueService;
    }

    @GetMapping(value = "/id/{idIssue}", produces = "application/json")
    public ResponseEntity<IssueDto> getIssueByIdIssue(@PathVariable("idIssue") int idIssue)
    {
        logger.info("****** Cerchiamo l'issue *******");

        IssueDto issue = issueService.selectByIdIssue(idIssue);

        if (issue == null)
            return new ResponseEntity<IssueDto>(HttpStatus.NO_CONTENT);

        logger.info("Issue trovata.");

        return new ResponseEntity<IssueDto>(issue, HttpStatus.OK);
    }

    @GetMapping(value = "/memberusername/{memberUsername}", produces = "application/json")
    public ResponseEntity<List<IssueInfoDto>> getIssuesByMemberUsername(@PathVariable("memberUsername") String memberUsername)
    {
        logger.info("****** Cerchiamo le issues assegnate ad un membro *******");

        List<IssueInfoDto> issues = issueService.selectByMemberUsername(memberUsername);

        if (issues.isEmpty())
            return new ResponseEntity<List<IssueInfoDto>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", issues.size());

        return new ResponseEntity<List<IssueInfoDto>>(issues, HttpStatus.OK);
    }

    @GetMapping(value = "/tag/{nameTag}", produces = "application/json")
    public ResponseEntity<List<IssueInfoDto>> getIssuesByTag(@PathVariable("nameTag") String nameTag)
    {
        logger.info("****** Cerchiamo le issues che hanno una certa tag *******");

        List<IssueInfoDto> issues = issueService.selectByTag(nameTag);

        if (issues.isEmpty())
            return new ResponseEntity<List<IssueInfoDto>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", issues.size());

        return new ResponseEntity<List<IssueInfoDto>>(issues, HttpStatus.OK);
    }

    @GetMapping(value = "/priority/{priority}", produces = "application/json")
    public ResponseEntity<List<IssueInfoDto>> getIssuesByPriority(@PathVariable("priority") int priority)
    {
        logger.info("****** Cerchiamo le issues con priorità {} *******", priority);

        List<IssueInfoDto> issues = issueService.selectByPriority(priority);

        if (issues.isEmpty())
            return new ResponseEntity<List<IssueInfoDto>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", issues.size());

        return new ResponseEntity<List<IssueInfoDto>>(issues, HttpStatus.OK);
    }

    @GetMapping(value = "/comments/id/{idIssue}", produces = "application/json")
    public ResponseEntity<IssueCommentsDto> getIssueCommentsByIdIssue(@PathVariable("idIssue") int idIssue)
    {
        logger.info("****** Cerchiamo l'issue *******");

        IssueCommentsDto issue = issueService.selectCommentsByIdIssue(idIssue);

        if (issue == null)
            return new ResponseEntity<IssueCommentsDto>(HttpStatus.NO_CONTENT);

        logger.info("Issue trovata.");

        return new ResponseEntity<IssueCommentsDto>(issue, HttpStatus.OK);
    }

    @GetMapping(value = "/logs/id/{idIssue}", produces = "application/json")
    public ResponseEntity<IssueLogsDto> getIssueLogsByIdIssue(@PathVariable("idIssue") int idIssue)
    {
        logger.info("****** Cerchiamo l'issue *******");

        IssueLogsDto issue = issueService.selectLogsByIdIssue(idIssue);

        if (issue == null)
            return new ResponseEntity<IssueLogsDto>(HttpStatus.NO_CONTENT);

        logger.info("Issue trovata.");

        return new ResponseEntity<IssueLogsDto>(issue, HttpStatus.OK);
    }
}
