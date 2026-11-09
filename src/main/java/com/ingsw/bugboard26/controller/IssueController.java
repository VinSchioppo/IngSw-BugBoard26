package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.issue.*;
import com.ingsw.bugboard26.exception.NotFoundException;
import com.ingsw.bugboard26.service.issue.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;

@RestController
@RequestMapping(value = "api/issues")
public class IssueController
{
    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    private final IssueService issueService;

    @Autowired
    public IssueController(final IssueService issueService)
    {
        this.issueService = issueService;
    }

    @GetMapping(value = "/{idIssue}", produces = "application/json")
    public ResponseEntity<IssueDto> getIssueByIdIssue(@PathVariable("idIssue") int idIssue)
            throws NotFoundException
    {
        logger.info("****** Cerchiamo la issue con id {} *******", idIssue);

        IssueDto issue = issueService.selectByIdIssue(idIssue);

        if (issue == null)
            throw new NotFoundException("L'issue con id " + idIssue + " non è stata trovata.");

        logger.info("Issue trovate.");

        return new ResponseEntity<IssueDto>(issue, HttpStatus.OK);
    }

    @GetMapping(params = "member", produces = "application/json")
    public ResponseEntity<List<IssueInfoDto>> getIssuesByMemberUsername(@RequestParam("member") String memberUsername)
    {
        logger.info("****** Cerchiamo le issues assegnate ad un membro *******");

        List<IssueInfoDto> issues = issueService.selectByMemberUsername(memberUsername);

        logger.info("Numero dei record: {}.", issues.size());

        return new ResponseEntity<List<IssueInfoDto>>(issues, HttpStatus.OK);
    }

    @GetMapping(params = "tag", produces = "application/json")
    public ResponseEntity<List<IssueInfoDto>> getIssuesByTag(@RequestParam(name = "tag") String nameTag)
    {
        logger.info("****** Cerchiamo le issues che hanno una certa tag *******");

        List<IssueInfoDto> issues = issueService.selectByTag(nameTag);

        logger.info("Numero dei record: {}.", issues.size());

        return new ResponseEntity<List<IssueInfoDto>>(issues, HttpStatus.OK);
    }

    @GetMapping(params = "priority", produces = "application/json")
    public ResponseEntity<List<IssueInfoDto>> getIssuesByPriority(@RequestParam(name = "priority") Integer priority)
    {
        logger.info("****** Cerchiamo le issues con priorità {} *******", priority);

        List<IssueInfoDto> issues = issueService.selectByPriority(priority);

        logger.info("Numero dei record: {}.", issues.size());

        return new ResponseEntity<List<IssueInfoDto>>(issues, HttpStatus.OK);
    }

    @GetMapping(value = "/comments/{idIssue}", produces = "application/json")
    public ResponseEntity<IssueCommentsDto> getIssueCommentsByIdIssue(@PathVariable("idIssue") int idIssue)
            throws NotFoundException
    {
        logger.info("****** Cerchiamo i commenti dell'issue *******");

        IssueCommentsDto issue = issueService.selectCommentsByIdIssue(idIssue);

        if (issue == null)
            throw new NotFoundException("L'issue con id " + idIssue + " non è stata trovata.");

        logger.info("Issue trovata.");

        return new ResponseEntity<IssueCommentsDto>(issue, HttpStatus.OK);
    }

    @GetMapping(value = "/logs/{idIssue}", produces = "application/json")
    public ResponseEntity<IssueLogsDto> getIssueLogsByIdIssue(@PathVariable("idIssue") int idIssue)
            throws NotFoundException
    {
        logger.info("****** Cerchiamo i logs dell'issue *******");

        IssueLogsDto issue = issueService.selectLogsByIdIssue(idIssue);

        if (issue == null)
            throw new NotFoundException("L'issue con id " + idIssue + " non è stata trovata.");

        logger.info("Logs trovati.");

        return new ResponseEntity<IssueLogsDto>(issue, HttpStatus.OK);
    }

    @PostMapping(value = "/member/{idIssue}")
    public ResponseEntity<IssueMemberDto> assignMember(
            @PathVariable("idIssue") int idIssue, @RequestBody IssueMemberDto member)
    {
        logger.info("****** Assegnamo la issue *******");

        if(member == null || member.getTeamMember() == null)
            return new ResponseEntity<IssueMemberDto>(HttpStatus.NO_CONTENT);

        issueService.insertMember(idIssue, member);

        logger.info("Issue assegnata con successo.");

        return new ResponseEntity<IssueMemberDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/logs/{idIssue}")
    public ResponseEntity<IssueLogsDto> logChange(
            @PathVariable("idIssue") int idIssue, @RequestBody IssueLogsDto logs)
    {
        logger.info("****** Salviamo i log della issue *******");

        if(logs == null || logs.getIssueLogs() == null || logs.getIssueLogs().isEmpty())
            return new ResponseEntity<IssueLogsDto>(HttpStatus.NO_CONTENT);

        issueService.insertLogs(idIssue, logs);

        logger.info("Log salvati con successo.");

        return new ResponseEntity<IssueLogsDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/status/{idIssue}")
    public ResponseEntity<IssueInfoDto> changeStatus(
            @PathVariable("idIssue") int idIssue, @RequestBody IssueInfoDto issue)
    {
        logger.info("****** Aggiorniamo lo stato dell'issue *******");

        if(issue == null)
            return new ResponseEntity<IssueInfoDto>(HttpStatus.NO_CONTENT);

        issueService.updateStatus(idIssue, issue);

        logger.info("Aggiornamento eseguito con successo.");

        return new ResponseEntity<IssueInfoDto>(new HttpHeaders(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{idIssue}")
    public ResponseEntity<Object> removeIssue(@PathVariable("idIssue") int idIssue)
            throws NotFoundException
    {
        logger.info("****** Eliminiamo l'issue con id {} ******", idIssue);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        issueService.deleteIssue(idIssue);

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione issue " + idIssue + " eseguita con successo!");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }
}
